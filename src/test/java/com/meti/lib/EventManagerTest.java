package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

import static com.meti.lib.EventManagerTest.TestKey.TEST_KEY;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class EventManagerTest {
    @Test
    void compound() {
        EventManager<TestKey, TestEvent> eventManager = new EventManager<>();

        ArrayList<TestEvent> list0 = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer0 = new CollectionConsumer<>(list0);
        assertFalse(eventManager.compound(TEST_KEY, consumer0).isPresent());

        ArrayList<TestEvent> list1 = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer1 = new CollectionConsumer<>(list1);

        Optional<Consumer<TestEvent>> optional = eventManager.compound(TEST_KEY, consumer1);
        assertTrue(optional.isPresent());
        assertEquals(consumer0, optional.get());
    }

    @Test
    void fire(){
        EventManager<TestKey, TestEvent> eventManager = new EventManager<>();

        ArrayList<TestEvent> list = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer = new CollectionConsumer<>(list);
        eventManager.compound(TEST_KEY, consumer);

        TestEvent event = new TestEvent();
        boolean wasConsumer = eventManager.fire(TEST_KEY, event);

        assertTrue(wasConsumer);
        assertEquals(1, list.size());
        assertTrue(list.contains(event));
    }

    enum TestKey {
        TEST_KEY
    }

    private static class EventManager<K extends Enum<?>, E extends EventManager.Event> extends HashMap<K, Consumer<E>> {
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

        private interface Event {
        }
    }

    private class TestEvent implements EventManager.Event {
    }
}
