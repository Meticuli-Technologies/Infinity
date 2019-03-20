package com.meti.lib.net;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class AbstractTokenHandler<T, R> implements TokenHandler<T, R> {
    private final Predicate<T> predicate;
    private final Function<T, R> function;

    public AbstractTokenHandler(Predicate<T> predicate, Function<T, R> function) {
        this.predicate = predicate;
        this.function = function;
    }

    @Override
    public R apply(T t) {
        return function.apply(t);
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }
}
