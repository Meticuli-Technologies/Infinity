package com.meti;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectionModule extends Module {
    private final Set<Class<?>> classes = new HashSet<>();

    public CollectionModule(String name, Class<?>... classes) {
        super(name);
        this.classes.addAll(Arrays.asList(classes));
    }

    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }

    @Override
    public <T> Set<Class<?>> getImplementations(Class<T> tClass) {
        return classes
                .stream()
                .filter(tClass::isAssignableFrom)
                .collect(Collectors.toSet());
    }
}
