package com.meti.lib.io.server.handle;

import com.meti.lib.io.source.Source;
import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;

public abstract class TypeTokenHandler<T, R> implements TokenHandler<Object, R> {
    private final TypePredicate<T> typePredicate;
    private final TypeFunction<T> typeFunction;

    public TypeTokenHandler(Class<T> tClass) {
        this.typePredicate = new TypePredicate<>(tClass);
        this.typeFunction = new TypeFunction<>(tClass);
    }

    @Override
    public R apply(Object o, Source source) {
        return handle(typeFunction.apply(o), source);
    }

    protected abstract R handle(T t, Source source);

    @Override
    public boolean test(Object o) {
        return typePredicate.test(o);
    }
}
