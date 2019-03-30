package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public interface Query<T> {
    Class<T> getReturnClass();
}
