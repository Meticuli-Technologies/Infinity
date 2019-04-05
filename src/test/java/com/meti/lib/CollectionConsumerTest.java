package com.meti.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class CollectionConsumerTest {
    @Test
    void accept(){
        ArrayList<String> list = new ArrayList<>();
        new CollectionConsumer<>(list).accept("test");

        assertEquals(1, list.size());
        assertEquals("test", list.get(0));
    }

    private class CollectionConsumer<T> implements Consumer<T> {
        private final Collection<T> collection;

        public CollectionConsumer(Collection<T> collection) {
            this.collection = collection;
        }

        @Override
        public void accept(T t) {
        }
    }
}
