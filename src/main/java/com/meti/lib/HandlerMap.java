package com.meti.lib;

import java.util.HashSet;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class HandlerMap<T> extends HashSet<Handler<T>> {
    public Stream<Handler<T>> process(T obj) {
        return stream()
                .filter(tHandler -> tHandler.test(obj))
                .peek(tHandler -> tHandler.accept(obj));
    }
}
