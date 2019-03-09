package com.meti.lib.handle;

import java.util.HashSet;

public class HandlerMap<T, H extends Handler<T>> extends HashSet<H> implements Handler<T> {
    @Override
    public void accept(T t) {
        stream().filter(h -> h.test(t))
                .forEach(h -> h.accept(t));
    }

    @Override
    public boolean test(T t) {
        return stream().anyMatch(h -> h.test(t));
    }
}
