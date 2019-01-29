package com.meti.lib.bucket;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Bucket<T> {
    private final Predicate<T> filter;
    private final Consumer<T> handler;

    public Bucket(Predicate<T> filter, Consumer<T> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    public boolean containsAllParameters(Object... objects) {
        for (int i = 0; i < objects.length; i++) {
            if (!containsParameter(objects[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean containsParameter(Object obj) {
        if (filter instanceof Parameterized<?>) {
            Object[] parameters = ((Parameterized) filter).getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].equals(obj)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Optional<Contentable<?, ?>> getContent() {
        if (handler instanceof Contentable) {
            return Optional.of((Contentable<?, ?>) handler);
        } else {
            return Optional.empty();
        }
    }

    public boolean test(T t) {
        return filter.test(t);
    }

    public boolean process(T t) {
        if (filter.test(t)) {
            handler.accept(t);
            return true;
        }
        return false;
    }
}
