package com.meti.lib.net;

import com.meti.lib.respond.CachedResponse;
import com.meti.lib.util.CollectionUtil;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ClientBuffer implements Callable<Optional<Exception>> {
    public final Set<TokenHandler<Object, ?>> handlers = new HashSet<>();

    private final Map<String, ArrayList<Update>> updateMap = new HashMap<>();
    private final Client client;

    public ClientBuffer(Client client) {
        this.client = client;

        this.handlers.add(new AbstractTokenHandler<>(
                new TypePredicate<>(Request.class),
                ((Function<Request, List<Update>>) request -> {
                    if (!updateMap.containsKey(request.key)) {
                        updateMap.put(request.key, new ArrayList<>());
                    }

                    List<Update> list = Collections.unmodifiableList(updateMap.get(request.key));
                    updateMap.get(request.key).clear();
                    return list;
                }).compose(new TypeFunction<>(Request.class))
        ));
    }

    @Override
    public Optional<Exception> call() {
        while (!Thread.interrupted()) {
            try {
                Object token = client.readUnshared();
                List<?> results = getResults(token);
                checkResults(token, results);
                client.writeUnshared(CollectionUtil.toSingle(results)
                        .orElse(null));
            } catch (IOException | ClassNotFoundException e) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    private void checkResults(Object token, List<?> results) throws IOException {
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

    private List<?> getResults(Object token) {
        return handlers.stream()
                .filter(handler -> handler.test(token))
                .map(handler -> handler.apply(token))
                .collect(Collectors.toList());
    }

    public void update(String key, Update update) {
        if (!updateMap.containsKey(key)) {
            updateMap.put(key, new ArrayList<>());
        }

        updateMap.get(key).add(update);
    }
}
