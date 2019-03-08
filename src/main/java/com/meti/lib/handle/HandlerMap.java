package com.meti.lib.handle;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HandlerMap<T> extends HashMap<Predicate<T>, Consumer<T>> {
    public void add(Handler<T> handler){
        put(handler, handler);
    }
}
