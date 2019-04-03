package com.meti.lib.event;

import com.meti.lib.trys.CollectionConsumer;

import java.util.*;
import java.util.function.Consumer;

public class EventManager<E extends Event> {
    private final Map<Integer, Consumer<E>> eventMap = new HashMap<>();

    public Collection<?> getUpdates(int key){
        if(!eventMap.containsKey(key)){
            return new ArrayList<>();
        }

        Consumer<E> consumer = eventMap.get(key);
        if(consumer instanceof CollectionConsumer<?, ?>){
            return ((CollectionConsumer<?, ?>) consumer).collection;
        }
        else{
            throw new IllegalStateException("Value at " + key + " was not an instance of CollectionConsumer.");
        }
    }

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
