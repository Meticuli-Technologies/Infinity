package com.meti.lib.util;

import java.util.function.Function;

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
