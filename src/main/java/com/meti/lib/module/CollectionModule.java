package com.meti.lib.module;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class CollectionModule extends Module {
    private final Collection<Class<?>> classes;

    public CollectionModule(String name, Class<?>... classes) {
        this(name, Arrays.asList(classes));
    }

    public CollectionModule(String name, Collection<Class<?>> classes) {
        super(name);
        this.classes = classes;
    }

    @Override
    public <T> Stream<Class<?>> getImplementations(Class<T> superClass) {
        return classes.stream().filter(superClass::isAssignableFrom);
    }
}
