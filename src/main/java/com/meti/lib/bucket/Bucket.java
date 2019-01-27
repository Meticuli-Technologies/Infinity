package com.meti.lib.bucket;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Bucket<T> {
    private final Predicate<T> filter;
    private final Consumer<T> handler;

    public Bucket(Predicate<T> filter, Consumer<T> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    public boolean test(T t) {
        return filter.test(t);
    }

    public boolean process(T t) {
        if (filter.test(t)) {
            handler.accept(t);
            return true;
        }
        return false;
    }
}
