package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
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
    void compoundNoPrevious() {
        EventManager<TestKey, TestEvent, CollectionConsumer<TestEvent>> eventManager = new EventManager<>();

        ArrayList<TestEvent> list0 = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer0 = new CollectionConsumer<>(list0);
        assertNull(eventManager.compound(TEST_KEY, consumer0));

        ArrayList<TestEvent> list1 = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer1 = new CollectionConsumer<>(list1);
        assertEquals(consumer0, eventManager.compound(TEST_KEY, consumer1));
    }

    @Test
    void fire(){
        EventManager<TestKey, TestEvent, CollectionConsumer<TestEvent>> eventManager = new EventManager<>();

        ArrayList<TestEvent> list = new ArrayList<>();
        CollectionConsumer<TestEvent> consumer = new CollectionConsumer<>(list);
        eventManager.compound(TEST_KEY, consumer);

        TestEvent event = new TestEvent("test");
        boolean wasConsumer = eventManager.fire(TEST_KEY, event);

        assertTrue(wasConsumer);
        assertEquals(1, list.size());
        assertTrue(list.contains(event));
    }

    enum TestKey {
        TEST_KEY
    }

    private static class EventManager<K extends Enum<?>, E extends EventManager.Event, C extends Consumer<E>> extends HashMap<K, C> {
        public C compound(K key, C consumer) {
            return null;
        }

        public boolean fire(K test0, E event) {
            return false;
        }

        private interface Event {
        }
    }

    private class TestEvent implements EventManager.Event {
        private final String value;

        private TestEvent(String value) {
            this.value = value;
        }
    }
}
