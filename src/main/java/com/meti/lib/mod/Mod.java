package com.meti.lib.mod;

import com.meti.lib.collect.TypeFunction;
import com.meti.lib.reflect.Instantiator;
import com.meti.lib.reflect.InstantiatorImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Mod implements ModImpl {
    private final List<Class<?>> classes = new ArrayList<>();

    Mod(Collection<Class<?>> classCollection) {
        this.classes.addAll(classCollection);
    }

    @Override
    public boolean containsClass(Class<?> clazz) {
        return classes.contains(clazz);
    }

    @Override
    public boolean containsImplementationClass(Class<?> clazz) {
        return classes.stream().anyMatch(clazz::isAssignableFrom);
    }

    @Override
    public <T> Set<T> instantiate(Class<? extends T> tClass, List<Object> dependencies) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Set<Class<?>> implementations = getImplementations(tClass);
        InstantiatorImpl instantiator = new Instantiator();
        Set<T> ts = new HashSet<>();
        for (Class<?> implementation : implementations) {
            List<Object> instances = instantiator.instantiate(implementation, dependencies);
            Set<T> instanceSet = createInstanceSet(tClass, instances);
            ts.addAll(instanceSet);
        }
        return ts;
    }

    private <T> Set<Class<?>> getImplementations(Class<? extends T> tClass) {
        return classes.stream()
                .filter(tClass::isAssignableFrom)
                .collect(Collectors.toSet());
    }

    private <T> Set<T> createInstanceSet(Class<? extends T> tClass, List<Object> instances) {
        return instances.stream()
                .map(new TypeFunction<>(tClass))
                .collect(Collectors.toSet());
    }
}
