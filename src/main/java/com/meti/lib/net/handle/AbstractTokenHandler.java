package com.meti.lib.net.handle;

import java.util.function.Function;
import java.util.function.Predicate;

public class AbstractTokenHandler<T> implements TokenHandler<T> {
    private final Predicate<T> predicate;
    private final Function<T, Object> function;

    public AbstractTokenHandler(Predicate<T> predicate, Function<T, Object> function) {
        this.predicate = predicate;
        this.function = function;
    }

    @Override
    public Object apply(T t) {
        return function.apply(t);
    }

    @Override
    public boolean test(T t) {
        return predicate.test(t);
    }
}
