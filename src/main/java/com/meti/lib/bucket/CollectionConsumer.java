package com.meti.lib.bucket;

import com.meti.lib.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;

public class CollectionConsumer<T, C extends Collection<T>> implements Container<T, C> {
    private final C collection;

    public CollectionConsumer(C collection) {
        this.collection = collection;
    }

    public static <T> CollectionConsumer<T, ArrayList<T>> empty() {
        return new CollectionConsumer<>(new ArrayList<>());
    }

    @Override
    public C getElements() {
        return collection;
    }

    @Override
    public T toSingle() {
        return CollectionUtil.toSingle(collection);
    }

    @Override
    public void accept(T t) {
        collection.add(t);
    }
}
