package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.evaluate.Evaluatable;
import com.meti.lib.net.server.evaluate.Evaluator;
import javafx.collections.MapChangeListener;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Set<Evaluatable<?>> collect = reflections.getSubTypesOf(Evaluatable.class).stream()
                .filter(aClass -> aClass.getAnnotation(Evaluator.class) != null)
                .map((Function<Class<? extends Evaluatable>, Evaluatable<?>>) aClass -> {
                    try {
                        return aClass.newInstance();
                    } catch (Exception e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        evaluatableSet.addAll(collect);
    }

    @Override
    public void acceptClient(Client<SocketConnection> client) {
        logger.info("Processing client " + client.connection.socket.getInetAddress());

        client.input.classListMap.addListener((MapChangeListener<Class<?>, List<Object>>) change -> {
            if (change.wasAdded()) {
                List<Object> value = change.getValueAdded();

                client.output.outputQueue.addAll(value.stream().flatMap((Function<Object, Stream<Serializable>>) o -> evaluatableSet.stream()
                        .filter(evaluatable -> evaluatable.canEvaluate(o))
                        .map(evaluatable -> evaluatable.evaluateObject(o))).collect(Collectors.toSet()));

                change.getMap().clear();
            }
        });
    }
}
