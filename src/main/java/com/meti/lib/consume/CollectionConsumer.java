package com.meti.lib.consume;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class CollectionConsumer<T> implements Consumer<T> {
    private final Collection<T> collection;

    public CollectionConsumer(Collection<T> collection) {
        this.collection = collection;
    }

    @Override
    public void accept(T t) {
        collection.add(t);
    }
}
