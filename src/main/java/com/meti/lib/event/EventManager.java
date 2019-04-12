package com.meti.lib.event;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager<E extends Event, T> extends HashMap<E, Consumer<T>> {
    public boolean fire(E event, T value) {
        Consumer<T> consumer = get(event);
        if (consumer == null) {
            return false;
        }
        consumer.accept(value);
        return true;
    }
}
