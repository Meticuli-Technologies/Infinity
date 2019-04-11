package com.meti.lib.net;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class AbstractHandler<T> implements Handler<T> {
    private final Predicate<Object> predicate;
    private final Function<Object, T> function;

    public AbstractHandler(Predicate<Object> predicate, Function<Object, T> function) {
        this.predicate = predicate;
        this.function = function;
    }

    @Override
    public T apply(Object o) {
        return function.apply(o);
    }

    @Override
    public boolean test(Object o) {
        return predicate.test(o);
    }
}
