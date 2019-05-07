package com.meti.lib.io.query;

import com.meti.app.control.client.view.ChatMessage;
import com.meti.lib.io.respond.OKResponse;
import com.meti.lib.util.collect.TypeFunction;
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
    public <T> CompletableFuture<T> query(Query<T> query) throws IOException, InterruptedException {
        return queryObject(query).thenApply(new TypeFunction<>(query.getTypeClass()));
    }

    private CompletableFuture<Object> queryObject(Object token) throws IOException, InterruptedException {
        channel.write(token);
        channel.flush();

        CompletableFuture<Object> future = new CompletableFuture<>();
        futures.put(future);
        return future;
    }

    private class QuerierListener implements Callable<Querier> {
        @Override
        public Querier call() throws Exception {
            while (channel.isOpen()) {
                Object token = channel.read();
                System.out.println(token);
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
