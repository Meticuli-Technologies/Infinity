package com.meti.lib.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class CompletableConsumer<T> implements Consumer<T> {
    private final CompletableFuture<T> future = new CompletableFuture<>();

    @Override
    public void accept(T t) {
        future.complete(t);
    }

    public T get() throws InterruptedException, ExecutionException {
        return future.get();
    }
}
