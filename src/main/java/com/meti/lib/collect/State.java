package com.meti.lib.collect;

import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public interface State {
    boolean add(Object o);

    <T> Optional<T> singleByClass(Class<T> tClass);

    <T> Set<T> filterByClass(Class<T> tClass);
}
