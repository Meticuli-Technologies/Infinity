package com.meti;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
class TokenHandler implements Callable<Void> {
    private final Client client;
    private Map<Predicate<Object>, Function<Object, Object>> handlers = new HashMap<>();

    public TokenHandler(Client client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while (!client.isClosed()) {
            Object token = client.readObject();
            Object toWrite;

            Set<Object> results = handlers.keySet()
                    .stream()
                    .filter(predicate -> predicate.test(token))
                    .map(handlers::get)
                    .map(objectObjectFunction -> objectObjectFunction.apply(token))
                    .collect(Collectors.toSet());

            try {
                toWrite = CollectionUtil.toSingle(results);
            } catch (Exception e) {
                toWrite = e;
            }

            client.writeObject(toWrite);
            client.flush();
        }
        return null;
    }
}
