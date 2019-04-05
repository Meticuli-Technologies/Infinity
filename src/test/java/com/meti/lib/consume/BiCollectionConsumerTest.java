package com.meti.lib.consume;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class BiCollectionConsumerTest {
    @Test
    void accept(){
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
}
