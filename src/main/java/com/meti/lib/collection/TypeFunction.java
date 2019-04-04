package com.meti.lib.collection;

import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class TypeFunction<T> implements Function<Object, T> {
    private final Class<T> tClass;

    public TypeFunction(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T apply(Object o) {
        return tClass.cast(o);
    }
}
