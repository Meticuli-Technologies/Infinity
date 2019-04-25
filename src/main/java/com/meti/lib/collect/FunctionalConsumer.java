package com.meti.lib.collect;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class FunctionalConsumer<T, R> implements Consumer<T>, Function<T, R> {
    private final Consumer<R> consumer;

    protected FunctionalConsumer(Consumer<R> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        consumer.accept(apply(t));
    }
}
