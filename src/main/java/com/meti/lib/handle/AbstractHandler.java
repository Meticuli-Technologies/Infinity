package com.meti.lib.handle;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AbstractHandler<T, P extends Predicate<T>, C extends Consumer<T>> implements Handler<T> {
    private P predicate;
    private C consumer;

    public AbstractHandler(P predicate, C consumer) {
        this.predicate = predicate;
        this.consumer = consumer;
    }

    @Override
    public void accept(T t) {
        getConsumer().orElseThrow().accept(t);
    }

    public Optional<C> getConsumer() {
        return Optional.ofNullable(consumer);
    }

    public void setConsumer(C consumer) {
        this.consumer = consumer;
    }

    public void setPredicate(P predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T t) {
        return getPredicate().orElseThrow().test(t);
    }

    public Optional<P> getPredicate() {
        return Optional.ofNullable(predicate);
    }
}
