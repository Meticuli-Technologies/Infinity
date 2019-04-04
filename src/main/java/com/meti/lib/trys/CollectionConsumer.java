package com.meti.lib.trys;

import java.util.Collection;
import java.util.function.Consumer;

public class CollectionConsumer<T, C extends Collection<T>> implements Consumer<T> {
    public final C collection;

    public CollectionConsumer(C collection) {
        this.collection = collection;
    }

    @Override
    public void accept(T e) {
        collection.add(e);
    }
}
