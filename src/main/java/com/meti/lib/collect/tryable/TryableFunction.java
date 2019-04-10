package com.meti.lib.collect.tryable;

public interface TryableFunction<T, R> {
    R apply(T t) throws Exception;
}
