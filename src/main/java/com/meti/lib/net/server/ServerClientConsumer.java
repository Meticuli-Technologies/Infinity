package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.evaluate.AbstractEvaluatable;
import com.meti.lib.net.server.evaluate.Evaluatable;
import com.meti.lib.net.server.evaluate.Evaluator;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public class ServerClientConsumer extends ClientConsumer<SocketConnection> {
    private final Set<Evaluatable<?>> evaluatableSet = new HashSet<>();
    private final Logger logger = LoggerFactory.getLogger(ServerClientConsumer.class);

    {
        Reflections reflections = new Reflections("com.meti");
        evaluatableSet.addAll(reflections.getSubTypesOf(Evaluatable.class).stream()
                .filter(aClass -> aClass.getAnnotation(Evaluator.class) != null)
                .map((Function<Class<? extends Evaluatable>, Evaluatable<?>>) aClass -> {
                    try {
                        return aClass.newInstance();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    @Override
    public void acceptClient(Client<SocketConnection> client) throws Exception {
        logger.info("Processing client " + client.connection.socket.getInetAddress());

        //TODO: handle closing bug
        boolean closed = false;
        do {
            try {
                Object input = client.connection.objectInputStream.readObject();
                Serializable result = parseInput(client, input, evaluatableSet.stream()
                        .filter(evaluatable -> evaluatable.canEvaluate(input))
                        .collect(Collectors.toSet()));

                client.connection.objectOutputStream.writeObject(result);
                client.connection.objectOutputStream.flush();
            } catch (SocketException e) {
                logger.warn("Socket at " + client.connection.socket.getInetAddress() + " closed");
                closed = true;
            }
        } while (server.runningProperty.get() && !closed);
    }

    private Serializable parseInput(Client<SocketConnection> client, Object object, Set<Evaluatable<?>> ableSet) {
        if (ableSet.size() == 0) {
            return new IllegalStateException("Unable to evaluate object " + object.toString());
        } else {
            ArrayList<Serializable> serializables = ableSet.stream()
                    .peek(evaluatable -> checkAbstractEvaluatable(evaluatable, client, server))
                    .map(evaluatable -> evaluatable.evaluateObject(object))
                    .collect(Collectors.toCollection(ArrayList::new));

            return serializables.size() == 1 ? serializables.get(0) : serializables;
        }
    }

    private boolean checkAbstractEvaluatable(Evaluatable<?> evaluatable, Client<SocketConnection> client, Server server) {
        if (evaluatable instanceof AbstractEvaluatable) {
            ((AbstractEvaluatable<?>) evaluatable).setClient(client);
            ((AbstractEvaluatable<?>) evaluatable).setServer(server);
            return true;
        } else {
            return false;
        }
    }
}
