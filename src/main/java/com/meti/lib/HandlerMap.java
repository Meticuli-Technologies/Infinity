package com.meti.lib;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public class HandlerMap<T> {
    private final Set<Handler<T>> handlers = new HashSet<>();

    public boolean add(Handler<T> tHandler) {
        return handlers.add(tHandler);
    }

    public Stream<Handler<T>> process(T obj) {
        return handlers.stream()
                .filter(tHandler -> tHandler.test(obj))
                .peek(tHandler -> tHandler.accept(obj));
    }
}
