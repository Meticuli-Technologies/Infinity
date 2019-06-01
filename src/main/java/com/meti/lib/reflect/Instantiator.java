package com.meti.lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/1/2019
 */
public class Instantiator {
    private final List<Object> dependencies = new ArrayList<>();

    public Instantiator(Collection<?> dependencies) {
        this.dependencies.addAll(dependencies);
    }

    public Set<Object> instantiate(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Constructor<?>> constructors = List.of(clazz.getConstructors());
        Map<List<Class<?>>, Constructor<?>> parameterTypeMap = new HashMap<>();
        for (Constructor<?> constructor : constructors) {
            parameterTypeMap.put(List.of(constructor.getParameterTypes()), constructor);
        }
        List<Constructor<?>> validConstructors = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, Constructor<?>> listConstructorEntry : parameterTypeMap.entrySet()) {
            if ((listConstructorEntry.getKey()).size() == dependencies.size()) {
                boolean shouldAdd = true;
                for (int i = 0; i < (listConstructorEntry.getKey()).size(); i++) {
                    if (!(listConstructorEntry.getKey()).get(i).isInstance(dependencies.get(i))) {
                        shouldAdd = false;
                    }
                }
                if (shouldAdd) {
                    validConstructors.add(listConstructorEntry.getValue());
                }
            }
        }
        Set<Object> instances = new HashSet<>();
        for (Constructor<?> validConstructor : validConstructors) {
            Object instance = validConstructor.newInstance(dependencies.toArray());
            instances.add(instance);
        }
        return instances;
    }
}
