package com.meti.lib.event;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

public class EventManager<E extends Event> extends HashMap<Integer, Consumer<E>> {
    public Optional<Consumer<E>> fireEvent(int key, E event) {
        if (containsKey(event)) {
            Consumer<E> consumer = get(key);
            consumer.accept(event);
            return Optional.of(consumer);
        } else {
            return Optional.empty();
        }
    }
}
