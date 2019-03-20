package com.meti.lib.net;

import com.meti.lib.respond.CachedResponse;
import com.meti.lib.util.CollectionUtil;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ClientBuffer implements Callable<Optional<Exception>> {
    public final Set<TokenHandler<Object, ?>> handlers = new HashSet<>();
    private final Client client;

    public ClientBuffer(Client client) {
        this.client = client;
    }

    @Override
    public Optional<Exception> call() {
        while (!Thread.interrupted()) {
            try {
                Object token = client.readUnshared();
                List<?> results = getResults(token);
                checkResults(token, results);
                client.writeObject(CollectionUtil.toSingle(results));
            } catch (IOException | ClassNotFoundException e) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    public void checkResults(Object token, List<?> results) throws IOException {
        if (results.isEmpty()) {
            client.writeUnshared(new CachedResponse<>(new IllegalStateException("No handlers for " + token)));
        }

        if (results.size() > 1) {
            String resultString = results.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n\t"));
            client.writeUnshared(new CachedResponse<>(new IllegalStateException("Multiple results were found:\n\t" + resultString)));
        }
    }

    public List<?> getResults(Object token) {
        return handlers.stream()
                .filter(handler -> handler.test(token))
                .map(handler -> handler.apply(token))
                .collect(Collectors.toList());
    }
}
