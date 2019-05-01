package com.meti.lib.util.tryable;

interface TryableSupplier<T> extends Tryable {
    T get() throws Exception;
}
