package com.meti.lib;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public interface TryableFunction<T, R> {
    R apply(T t);
}
