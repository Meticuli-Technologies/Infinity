package com.meti.lib.trys;

import com.meti.lib.util.CollectionUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
class CollectionConsumerTest {

    @Test
    void accept() {
        CollectionConsumer<Object, ArrayList<Object>> consumer = new CollectionConsumer<>(new ArrayList<>());
        consumer.accept("test");

        Optional<Object> optional = CollectionUtil.toSingle(consumer.collection);
        assertTrue(optional.isPresent());
        assertEquals("test", optional.get());
    }
}