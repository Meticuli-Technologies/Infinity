package com.meti.lib.reflect;

import com.meti.lib.collect.TypeFunction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Instantiator extends InstantiatorImpl {
    @Override
    public <T> List<T> instantiateGeneric(Class<T> tClass, List<Object> dependencies) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return instantiate(tClass, dependencies).stream()
                .map(new TypeFunction<>(tClass))
                .collect(Collectors.toList());
    }

    @Override
    public List<Object> instantiate(Class<?> instantiatee, List<Object> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<List<Class<?>>, Constructor<?>> parameterTypeMap = constructParameterTypeMap(instantiatee);
        List<Class<?>> dependencyClasses = getDependencyClasses(dependencies);
        List<? extends Constructor<?>> constructors = matchConstructors(parameterTypeMap, dependencyClasses);
        return instantiateConstructors(dependencies, constructors);
    }

    private Map<List<Class<?>>, Constructor<?>> constructParameterTypeMap(Class<?> instantiatee) {
        Map<List<Class<?>>, Constructor<?>> parameterTypeMap = new HashMap<>();
        for (Constructor<?> constructor : instantiatee.getConstructors()) {
            parameterTypeMap.put(List.of(constructor.getParameterTypes()), constructor);
        }
        return parameterTypeMap;
    }

    private List<Class<?>> getDependencyClasses(List<Object> dependencies) {
        return dependencies.stream().map(Object::getClass).collect(Collectors.toList());
    }

    private List<Constructor<?>> matchConstructors(Map<List<Class<?>>, Constructor<?>> parameterTypeMap, List<Class<?>> dependencyClasses) {
        return parameterTypeMap.keySet().stream()
                .filter(classes -> matchParameters(classes, dependencyClasses))
                .map(parameterTypeMap::get)
                .collect(Collectors.toList());
    }

    private List<Object> instantiateConstructors(List<Object> dependencies, List<? extends Constructor<?>> constructors) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        List<Object> instances = new ArrayList<>();
        for (Constructor<?> constructor : constructors) {
            instances.add(constructor.newInstance(dependencies));
        }
        return instances;
    }

    private boolean matchParameters(List<Class<?>> superClasses, List<Class<?>> implementors) {
        if (superClasses.size() != implementors.size()) throw new IllegalArgumentException("List sizes aren't equal.");
        for (int i = 0; i < superClasses.size(); i++) {
            Class<?> superClass = superClasses.get(i);
            Class<?> implementor = implementors.get(i);
            if (!superClass.isAssignableFrom(implementor)) {
                return false;
            }
        }
        return true;
    }
}