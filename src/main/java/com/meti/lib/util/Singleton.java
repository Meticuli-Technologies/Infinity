package com.meti.lib.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Singleton<T> {
    private T item;

    public T get() {
        return item;
    }

    public void set(T item) {
        if (this.item == null) {
            this.item = item;
        } else {
            throw new IllegalArgumentException("Item has already been set");
        }
    }
}
