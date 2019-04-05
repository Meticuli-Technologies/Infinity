package com.meti.lib.event;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class EventManager<K extends Enum<?>, E extends Event> extends HashMap<K, Consumer<E>> {
    public Optional<Consumer<E>> compound(K key, Consumer<E> consumer) {
        Consumer<E> previous = null;
        Consumer<E> present;

        if (containsKey(key)) {
            previous = get(key);
            present = previous.andThen(consumer);
        } else {
            present = consumer;
        }
        put(key, present);

        return Optional.ofNullable(previous);
    }

    public boolean fire(K test0, E event) {
        if (containsKey(test0)) {
            get(test0).accept(event);
            return true;
        }
        return false;
    }

}
