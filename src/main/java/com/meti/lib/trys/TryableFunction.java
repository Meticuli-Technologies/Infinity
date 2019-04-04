package com.meti.lib.trys;

public interface TryableFunction<T, R> extends Tryable{
    R apply(T t) throws Exception;
}
