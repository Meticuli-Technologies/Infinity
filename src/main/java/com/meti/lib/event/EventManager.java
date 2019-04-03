package com.meti.lib.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class EventManager<E extends Event> {
    private final Map<Integer, Consumer<E>> eventMap = new HashMap<>();

    public boolean compound(int key, Consumer<E> after) {
        if (eventMap.containsKey(key)) {
            eventMap.get(key).andThen(after);
            return true;
        } else {
            eventMap.put(key, after);
            return false;
        }
    }

    public Optional<Consumer<E>> fireEvent(int key, E event) {
        if (eventMap.containsKey(key)) {
            Consumer<E> consumer = eventMap.get(key);
            consumer.accept(event);
            return Optional.of(consumer);
        } else {
            return Optional.empty();
        }
    }
}
