package com.meti.app;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketException;
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
public class InfinityConsumer extends ClientConsumer<SocketConnection> {
    private final Set<Evaluator> evaluatorSet = new HashSet<>();
    private final Logger logger = LoggerFactory.getLogger(InfinityConsumer.class);

    {
        Reflections reflections = new Reflections("com.meti");
        evaluatorSet.addAll(reflections.getSubTypesOf(Evaluator.class).stream()
                .map((Function<Class<? extends Evaluator>, Evaluator>) aClass -> {
                    try {
                        return aClass.newInstance();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
        );
    }

    @Override
    public void acceptClient(Client<SocketConnection> client) throws Exception {
        logger.info("Processing client " + client.connection.socket.getInetAddress());
        while (true) {
            try {
                Object object = client.connection.objectInputStream.readObject();
                Set<Evaluator> ableSet = evaluatorSet.stream().filter(evaluator -> evaluator.canEvaluate(object)).collect(Collectors.toSet());

                if (ableSet.size() == 0) {
                    throw new IllegalStateException("Unable to evaluate object " + object.toString());
                } else {
                    for (Evaluator evaluator : ableSet) {
                        evaluator.evaluate(object);
                    }
                }
            } catch (SocketException e) {
                break;
            }
        }
    }
}
