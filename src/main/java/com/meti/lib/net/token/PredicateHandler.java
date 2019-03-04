package com.meti.lib.net.token;

import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public abstract class PredicateHandler<T> implements TokenHandler<T> {
    private final Predicate<T> predicate;

    protected PredicateHandler(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }
}
