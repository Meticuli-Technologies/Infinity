package com.meti.lib.handle;

import com.meti.lib.collect.TypeFunction;
import com.meti.lib.collect.TypePredicate;
import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class TypeHandler<T, R extends ReadableSource<ObjectInputStream>> implements Handler<Object, R> {
    private final Predicate<Object> predicate;
    private final Function<Object, T> function;

    public TypeHandler(Class<T> tClass){
        this.predicate = new TypePredicate<>(tClass);
        this.function = new TypeFunction<>(tClass);
    }

    @Override
    public boolean canHandle(Object token) {
        return predicate.test(token);
    }

    @Override
    public void handle(Object token, R readable) {
        this.handleGeneric(function.apply(token), readable);
    }

    protected abstract void handleGeneric(T token, R readable);
}
