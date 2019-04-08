package com.meti.lib.collect.tryable;

public interface TryableSupplier<T> {
    T get() throws Exception;
}
