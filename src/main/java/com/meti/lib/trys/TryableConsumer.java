package com.meti.lib.trys;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public interface TryableConsumer<T> extends Tryable {
    void accept(T t) throws Exception;
}
