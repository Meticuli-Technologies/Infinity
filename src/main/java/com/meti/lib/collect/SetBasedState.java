package com.meti.lib.collect;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class SetBasedState implements State {
    private final Set<Object> components = new HashSet<>();

    @Override
    public void add(Object o) {
        components.add(o);
    }

    @Override
    public <T> Optional<T> singleByClass(Class<T> tClass) {
        return filterByClass(tClass).stream().findAny();
    }

    @Override
    public <T> Set<T> filterByClass(Class<T> tClass) {
        return components.stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .collect(Collectors.toSet());
    }
}
