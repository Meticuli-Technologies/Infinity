package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import com.meti.lib.net.server.evaluate.AbstractEvaluatable;
import com.meti.lib.net.server.evaluate.Evaluatable;
import com.meti.lib.net.server.evaluate.Evaluator;
import javafx.collections.ListChangeListener;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashSet;
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
                        return aClass.getDeclaredConstructor().newInstance();
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

        StringBuilder builder = new StringBuilder();
        builder.append("Located ").append(evaluatableSet.size()).append(" evaluatables to load");
        evaluatableSet.stream()
                .peek(evaluatable -> builder.append("\n\t").append(evaluatable.getParameterClass().getSimpleName()))
                .map(evaluatable -> client.input.ensureContains(evaluatable.getParameterClass()))
                .forEach(objects -> objects.addListener((ListChangeListener<Object>) c -> {
                    c.next();
                    Set<Serializable> collect = c.getAddedSubList().stream().flatMap((Function<Object, Stream<Serializable>>) o ->
                            evaluatableSet.stream()
                                    .peek(evaluatable -> {
                                        if (AbstractEvaluatable.class.isAssignableFrom(evaluatable.getClass())) {
                                            ((AbstractEvaluatable<?>) evaluatable).setServer(server);
                                            ((AbstractEvaluatable<?>) evaluatable).setClient(client);
                                        }
                                    })
                                    .filter(evaluatable -> evaluatable.canEvaluate(o))
                                    .map(evaluatable -> evaluatable.evaluateObject(o))).collect(Collectors.toSet());
                    client.output.outputQueue.addAll(collect);
                }));
        logger.info(builder.toString());
    }

}
