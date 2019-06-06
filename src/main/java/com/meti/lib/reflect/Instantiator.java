package com.meti.lib.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <T> Set<T> genericInstanceSet(List<?> dependencies, Class<? extends T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return genericInstanceStream(dependencies, clazz).collect(Collectors.toSet());
    }

    private static <T> Stream<? extends T> genericInstanceStream(List<?> dependencies, Class<? extends T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return new Instantiator(dependencies)
                .instantiate(clazz)
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }

    private Set<Object> instantiate(Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        List<Constructor<?>> constructors = List.of(clazz.getConstructors());
        Map<List<Class<?>>, Constructor<?>> parameterTypeMap = createParameterTypeMap(constructors);
        List<Constructor<?>> validConstructors = filterValidConstructors(parameterTypeMap);
        return instantiate(validConstructors);
    }

    public static <T> Optional<? extends T> genericInstanceToSingle(List<?> dependencies, Class<? extends T> clazz) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return genericInstanceStream(dependencies, clazz).findAny();
    }

    private Map<List<Class<?>>, Constructor<?>> createParameterTypeMap(Iterable<Constructor<?>> constructors) {
        Map<List<Class<?>>, Constructor<?>> parameterTypeMap = new HashMap<>();
        for (Constructor<?> constructor : constructors) {
            parameterTypeMap.put(List.of(constructor.getParameterTypes()), constructor);
        }
        return parameterTypeMap;
    }

    private List<Constructor<?>> filterValidConstructors(Map<List<Class<?>>, Constructor<?>> parameterTypeMap) {
        List<Constructor<?>> validConstructors = new ArrayList<>();
        for (Map.Entry<List<Class<?>>, Constructor<?>> parameterTypeEntry : parameterTypeMap.entrySet()) {
            if (areValidParameters(parameterTypeEntry.getKey())) {
                validConstructors.add(parameterTypeEntry.getValue());
            }
        }
        return validConstructors;
    }

    private Set<Object> instantiate(Iterable<Constructor<?>> validConstructors) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Set<Object> instances = new HashSet<>();
        for (Constructor<?> validConstructor : validConstructors) {
            Object instance = validConstructor.newInstance(dependencies.toArray());
            instances.add(instance);
        }
        return instances;
    }

    private boolean areValidParameters(List<Class<?>> key) {
        return isEqualSize(key, dependencies) && areDependenciesInstancesOf(key);
    }

    private boolean isEqualSize(Collection<?> collection0, Collection<?> collection1) {
        return collection0.size() == collection1.size();
    }

    @SuppressWarnings("MethodWithMultipleReturnPoints")
    private boolean areDependenciesInstancesOf(List<Class<?>> parameterTypes) {
        for (int i = 0; i < (parameterTypes).size(); i++) {
            if (!parameterTypes.get(i).isInstance(dependencies.get(i))) {
                return false;
            }
        }
        return true;
    }
}
