package com.meti.lib.event;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventMap<T> extends HashMap<Predicate<T>, Consumer<T>> {
    public void add(Event<T> event){
        put(event, event);
    }
}
