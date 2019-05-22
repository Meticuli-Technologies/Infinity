package com.meti.core.state;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface StateImpl {
    <T> Stream<T> filterByClass(Class<T> tClass);

    void add(Object object);

    void addAll(Collection<?> collection);

    <T> T getInstance(Class<T> tClass);
}
