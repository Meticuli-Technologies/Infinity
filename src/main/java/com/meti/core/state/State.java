package com.meti.core.state;

import com.meti.lib.collect.TypeFunction;
import com.meti.lib.collect.TypePredicate;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class State implements StateImpl {
    private final Set<Object> components = new HashSet<>();

    @Override
    public <T> Stream<T> filterByClass(Class<T> tClass){
        return components.stream()
                .filter(new TypePredicate<>(tClass))
                .map(new TypeFunction<>(tClass));
    }

    @Override
    public void add(Object object) {
        if (components.contains(object)) {
            //TODO: internationalize
            throw new IllegalArgumentException("Already contains " + object);
        }
        components.add(object);
    }

    @Override
    public void addAll(Collection<?> collection) {
        collection.forEach(this::add);
    }

    @Override
    public <T> T getInstance(Class<T> tClass) {
        return filterByClass(tClass).findAny().orElseThrow();
    }
}
