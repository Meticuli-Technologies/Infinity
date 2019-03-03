package com.meti.lib.net.token;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class CollectionHandler<T, C extends Collection<T>> extends TokenHandler<T> {
    private final C collection;

    public CollectionHandler(Class<T> tClass, C collection) {
        super(tClass);
        this.collection = collection;
    }

    @Override
    public void accept(T t) {
        collection.add(t);
    }

    public static <T> CollectionHandler<T, ArrayList<T>> fromList(Class<T> tClass) {
        return new CollectionHandler<>(tClass, new ArrayList<>());
    }
}
