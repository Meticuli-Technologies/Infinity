package com.meti.lib.net.query;

import com.meti.lib.net.object.ObjectClient;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class Querier implements Callable<Void> {
    private static final int DEFAULT_CAPACITY = 16;
    private final BlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(DEFAULT_CAPACITY);
    private final ObjectClient client;

    public Querier(ObjectClient client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while (client.isOpen()) {
            futures.take().complete(client.readObject());
        }
        return null;
    }

    public Future<Object> query(Query<?> query) throws IOException {
        client.writeObject(query);

        CompletableFuture<Object> future = new CompletableFuture<>();
        futures.add(future);
        return future;
    }
}
