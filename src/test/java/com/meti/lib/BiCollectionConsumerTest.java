package com.meti.lib;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class BiCollectionConsumerTest {
    @Test
    void accept(){

    }

    private class BiCollectionConsumer<K, V, M extends Map<K, V>> implements BiConsumer<K, V> {
        private final M map;

        private BiCollectionConsumer(M map) {
            this.map = map;
        }

        @Override
        public void accept(K k, V v) {
        }
    }
}
