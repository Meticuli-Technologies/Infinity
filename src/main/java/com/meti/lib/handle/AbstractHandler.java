package com.meti.lib.handle;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AbstractHandler<T> implements Handler<T> {
    private Predicate<T> predicate;
    private Consumer<T> consumer;

    public AbstractHandler(Predicate<T> predicate, Consumer<T> consumer) {
        this.predicate = predicate;
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        getConsumer().orElseThrow().accept(t);
    }

    private Optional<Consumer<T>> getConsumer() {
        return Optional.ofNullable(consumer);
    }

    public void setConsumer(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public void setPredicate(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T t) {
        return getPredicate().orElseThrow().test(t);
    }

    private Optional<Predicate<T>> getPredicate() {
        return Optional.ofNullable(predicate);
    }
}
