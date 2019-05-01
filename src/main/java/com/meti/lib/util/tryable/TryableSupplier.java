package com.meti.lib.util.tryable;

public interface TryableSupplier<T> extends Tryable {
    T get() throws Exception;
}
