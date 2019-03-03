package com.meti.lib;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public interface TryableConsumer<T> {
    void accept(T t) throws Exception;
}
