package com.meti.lib.handle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class CollectionConsumer<T, C extends Collection<T>> implements Consumer<T> {
    private final C collection;

    public CollectionConsumer(C collection) {
        this.collection = collection;
    }

    public static <T> CollectionConsumer<T, List<T>> asList() {
        return new CollectionConsumer<>(new ArrayList<>());
    }

    @Override
    public void accept(T t) {
        collection.add(t);
    }

    public C getCollection() {
        return collection;
    }
}
