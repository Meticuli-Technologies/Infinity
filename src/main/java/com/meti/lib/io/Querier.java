package com.meti.lib.io;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

public class Querier {
    private final ArrayBlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);
    private final ObjectSource<?> source;

    public Querier(ObjectSource<?> source) {
        this.source = source;
    }

    public QuerierListener getListener() {
        return new QuerierListener();
    }

    public CompletableFuture<Object> query(Object token){
        source.getOutputStream().writeObject(token);

    }

    private class QuerierListener implements Callable<Querier> {
        @Override
        public Querier call() throws Exception {
            while (source.isOpen()) {
                Object token = source.getInputStream().readObject();
                completeFuture(token, futures.take());
            }
            return Querier.this;
        }

        public void completeFuture(Object token, CompletableFuture<Object> future) {
            if (token instanceof Exception) {
                future.completeExceptionally((Throwable) token);
            } else {
                future.complete(token);
            }
        }
    }
}
