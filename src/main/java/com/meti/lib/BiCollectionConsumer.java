package com.meti.lib;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class BiCollectionConsumer<K, V, C extends Collection<V>, M extends Map<K, C>> implements BiConsumer<K, V> {
    private final Supplier<C> emptyCollectionSupplier;
    final M map;

    BiCollectionConsumer(Supplier<C> emptyCollectionSupplier, M map) {
        this.emptyCollectionSupplier = emptyCollectionSupplier;
        this.map = map;
    }

    @Override
    public void accept(K k, V v) {
        if (map.containsKey(k)) {
            map.get(k).add(v);
        } else {
            C collection = emptyCollectionSupplier.get();
            collection.add(v);
            map.put(k, collection);
        }
    }
}
