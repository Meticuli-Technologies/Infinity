package com.meti.lib.handle;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class HandlerMap<T, H extends Handler<T>> extends HashMap<Predicate<T>, Consumer<T>> {
    public void add(H handler){
        put(handler, handler);
    }
}
