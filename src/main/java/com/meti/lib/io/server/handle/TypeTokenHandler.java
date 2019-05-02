package com.meti.lib.io.server.handle;

import com.meti.lib.io.source.Source;
import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;

public abstract class TypeTokenHandler<T, S extends Source, R> implements TokenHandler<Object, S, R> {
    private final TypePredicate<T> typePredicate;
    private final TypeFunction<T> typeFunction;

    public TypeTokenHandler(Class<T> tClass) {
        this.typePredicate = new TypePredicate<>(tClass);
        this.typeFunction = new TypeFunction<>(tClass);
    }

    @Override
    public R apply(Object o, S s) {
        return handle(typeFunction.apply(o), s);
    }

    protected abstract R handle(T t, S source);

    @Override
    public boolean test(Object o) {
        return typePredicate.test(o);
    }
}
