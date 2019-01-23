package com.meti.lib;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Bucket<T> {
    private final Predicate<T> validator;
    private final Consumer<T> handler;

    public Bucket(Predicate<T> validator, Consumer<T> handler) {
        this.validator = validator;
        this.handler = handler;
    }

    public Set<?> getParameters() {
        if (validator instanceof Parameterized) {
            return ((Parameterized) validator).getParameters();
        } else {
            throw new UnsupportedOperationException(validator + " does not contain any parameters");
        }
    }

    public Collection<?> getElements() {
        if (handler instanceof Container<?, ?>) {
            return ((Container) handler).getElements();
        } else {
            throw new UnsupportedOperationException(handler + " does not contain any elements");
        }
    }

    public void handle(T t) {
        if (isValid(t)) {
            handler.accept(t);
        } else {
            throw new IllegalArgumentException(t + " is not valid under " + validator);
        }
    }

    public boolean isValid(T t) {
        return validator.test(t);
    }
}
