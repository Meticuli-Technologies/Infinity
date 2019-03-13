package com.meti.lib;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public abstract class FutureConsumer<T> implements Consumer<T>, Runnable {
    private final Future<T> future;

    protected FutureConsumer(Future<T> future) {
        this.future = future;
    }

    @Override
    public void run() {
        try {
            accept(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
