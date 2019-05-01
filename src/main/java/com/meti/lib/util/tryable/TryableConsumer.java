package com.meti.lib.util.tryable;

public interface TryableConsumer<T> extends Tryable {
    void accept(T t) throws Exception;
}
