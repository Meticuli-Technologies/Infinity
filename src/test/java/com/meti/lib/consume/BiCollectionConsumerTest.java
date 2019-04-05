package com.meti.lib.consume;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class BiCollectionConsumerTest {
    @Test
    void getCollection() {
        ArrayList<String> list = new ArrayList<>();
        BiCollectionConsumer<Integer, String, ArrayList<String>, HashMap<Integer, ArrayList<String>>> consumer = new BiCollectionConsumer<>(() -> list, new HashMap<>());
        Optional<ArrayList<String>> optional0 = consumer.getCollection(0);
        assertFalse(optional0.isPresent());

        consumer.accept(0, "test");
        Optional<ArrayList<String>> optional1 = consumer.getCollection(0);
        assertTrue(optional1.isPresent());
        assertEquals(list, optional1.get());

        assertEquals(1, list.size());
        assertTrue(list.contains("test"));
    }

    @Test
    void acceptNotPresent(){
        BiCollectionConsumer<Integer, String, ArrayList<String>, HashMap<Integer, ArrayList<String>>> consumer = new BiCollectionConsumer<>(ArrayList::new, new HashMap<>());
        consumer.accept(0, "test");

        assertEquals(1, consumer.map.size());
        assertTrue(consumer.map.containsKey(0));

        Optional<ArrayList<String>> optional = consumer.getCollection(0);
        assertTrue(optional.isPresent());

        ArrayList<String> list = optional.get();
        assertEquals(1, list.size());
        assertTrue(list.contains("test"));
    }

    @Test
    void acceptPresent(){
        BiCollectionConsumer<Integer, String, ArrayList<String>, HashMap<Integer, ArrayList<String>>> consumer = new BiCollectionConsumer<>(ArrayList::new, new HashMap<>());
        consumer.accept(0, "test0");
        consumer.accept(0, "test1");

        assertEquals(1, consumer.map.size());
        assertTrue(consumer.map.containsKey(0));

        Optional<ArrayList<String>> optional = consumer.getCollection(0);
        assertTrue(optional.isPresent());

        ArrayList<String> list = optional.get();
        assertEquals(2, list.size());
        assertTrue(list.contains("test0"));
        assertTrue(list.contains("test1"));
    }
}
