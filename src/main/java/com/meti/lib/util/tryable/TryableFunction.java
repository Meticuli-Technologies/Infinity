package com.meti.lib.util.tryable;

public interface TryableFunction<T, R> extends Tryable {
    R apply(T t);
}
