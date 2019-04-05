package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class EventManagerTest {
    @Test
    void compound(){

    }

    @Test
    void fire(){

    }

    private static class EventManager<K extends Enum<?>, E extends EventManager.Event, C extends Consumer<E>> extends HashMap<K, C> {
        private interface Event {
        }
    }
}
