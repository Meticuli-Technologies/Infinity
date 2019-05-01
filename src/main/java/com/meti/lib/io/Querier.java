package com.meti.lib.io;

import com.meti.lib.collect.TypeFunction;
import com.meti.lib.io.channel.ObjectChannel;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class Querier {
    private final ArrayBlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);
    private final ObjectChannel channel;

    public Querier(ObjectChannel channel) {
        this.channel = channel;
    }

    public QuerierListener getListener() {
        return new QuerierListener();
    }

    //TODO: use this method
    public <T> CompletableFuture<T> query(Object token, Class<T> tClass) throws IOException {
        return query(token).thenApply(new TypeFunction<>(tClass));
    }

    private CompletableFuture<Object> query(Object token) throws IOException {
        channel.write(token);
        channel.flush();

        CompletableFuture<Object> future = new CompletableFuture<>();
        futures.offer(future);
        return future;
    }

    private class QuerierListener implements Callable<Querier> {
        @Override
        public Querier call() throws Exception {
            while (channel.isOpen()) {
                Object token = channel.read();
                completeFuture(token, futures.take());
            }
            return Querier.this;
        }

        void completeFuture(Object token, CompletableFuture<Object> future) {
            if (token instanceof Exception) {
                future.completeExceptionally((Throwable) token);
            } else {
                future.complete(token);
            }
        }
    }
}
