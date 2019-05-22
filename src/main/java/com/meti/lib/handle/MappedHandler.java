package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class MappedHandler<T, R extends ReadableSource<ObjectInputStream>> implements MappedHandlerImpl<T, R> {
    private final Set<Handler<T, R>> subHandlers = new HashSet<>();

    @Override
    public boolean canHandle(T token) {
        return subHandlers.stream()
                .anyMatch(handler -> handler.canHandle(token));
    }

    @Override
    public void handle(T token, R readable) {
        subHandlers.stream()
                .filter(handler -> handler.canHandle(token))
                .forEach(handler -> handler.handle(token, readable));
    }

    @Override
    public void add(Handler<T, R> subHandler) {
        subHandlers.add(subHandler);
    }
}
