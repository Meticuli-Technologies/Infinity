package com.meti.lib.io.server.handle;

import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;

public abstract class TypeHandler<T, R> implements Handler<Object, R> {
    private final TypePredicate<T> typePredicate;
    private final TypeFunction<T> typeFunction;

    public TypeHandler(Class<T> tClass) {
        this.typePredicate = new TypePredicate<>(tClass);
        this.typeFunction = new TypeFunction<>(tClass);
    }

    @Override
    public R apply(Object o) {
        return handle(typeFunction.apply(o));
    }

    protected abstract R handle(T o);

    @Override
    public boolean test(Object o) {
        return typePredicate.test(o);
    }
}
