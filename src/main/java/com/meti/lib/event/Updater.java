package com.meti.lib.event;

import com.meti.lib.net.Client;
import com.meti.lib.net.query.Update;
import com.meti.lib.trys.CollectionConsumer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Updater<E extends Event> {
    private final Client<?> client;
    private final EventManager<E> eventManager;
    private final Map<Integer, Boolean> hasUpdated = new HashMap<>();

    public Updater(Client<?> client, EventManager<E> eventManager) {
        this.client = client;
        this.eventManager = eventManager;
    }

    public void register(int key) {
        eventManager.compound(key, e -> hasUpdated.put(key, true));
    }

    public boolean hasUpdate(int key) {
        return hasUpdated.get(key);
    }

    public Update getUpdate(int key) {
        if (hasUpdate(key)) {
            Map<Integer, ? extends Consumer<?>> eventMap = eventManager.eventMap;
            if (!eventMap.containsKey(key)) {
                return new Update();
            }

            Consumer<?> consumer = eventMap.get(key);
            if (consumer instanceof CollectionConsumer<?, ?>) {
                hasUpdated.put(key, false);
                return Update.of(((CollectionConsumer<?, ?>) consumer).collection);
            } else {
                throw new IllegalStateException("Value at " + key + " was not an instance of CollectionConsumer.");
            }
        } else {
            return new Update();
        }
    }
}
