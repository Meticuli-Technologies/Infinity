package com.meti.lib.collect.consume;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class CollectionConsumer<T, C extends Collection<T>> implements Consumer<T> {
    public final C collection;

    public CollectionConsumer(C collection) {
        this.collection = collection;
    }

    @Override
    public void accept(T t) {
        collection.add(t);
    }
}
