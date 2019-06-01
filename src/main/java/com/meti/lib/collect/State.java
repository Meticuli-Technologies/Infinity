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
public class State {
    private final Set<Object> components = new HashSet<>();

    public boolean add(Object o) {
        return components.add(o);
    }

    public <T> Optional<T> singleByClass(Class<T> tClass) {
        return filterByClass(tClass).stream().findAny();
    }

    private <T> Set<T> filterByClass(Class<T> tClass) {
        return components.stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .collect(Collectors.toSet());
    }
}
