package com.meti.lib.net;

import com.meti.lib.collect.TypeFunction;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/26/2019
 */
public class Querier implements Callable<Void> {
    private final ArrayBlockingQueue<CompletableFuture<Object>> queue = new ArrayBlockingQueue<>(16);
    private final ObjectChannel channel;
    private final Client client;

    public Querier(boolean shared, Client client) {
        this.client = client;
        this.channel = shared ?
                client.sharedChannel() :
                client.unsharedChannel();
    }

    @Override
    public Void call() throws Exception {
        while (client.isOpen()) {
            completeToken(channel.read());
        }
        return null;
    }

    private void completeToken(Object token) throws InterruptedException {
        if (token instanceof Exception) {
            queue.take().completeExceptionally((Exception) token);
        } else {
            queue.take().complete(token);
        }
    }

    public <T> CompletableFuture<T> query(Object request, Class<T> tClass) throws IOException {
        return query(request).thenApply(new TypeFunction<>(tClass));
    }

    public CompletableFuture<Object> query(Object request) throws IOException {
        channel.write(request);
        channel.flush();
        return newFuture();
    }

    private CompletableFuture<Object> newFuture() {
        CompletableFuture<Object> future = new CompletableFuture<>();
        queue.add(future);
        return future;
    }
}
