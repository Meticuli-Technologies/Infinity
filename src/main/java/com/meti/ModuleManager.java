package com.meti;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleManager {
    private final Set<Module> modules = new HashSet<>();

    public ModuleManager addModule(Module module) {
        modules.add(module);
        return this;
    }

    public <T> Set<T> constructInstances(Class<T> tClass, Object... parameters) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Class<?>> implementations = getImplementations(tClass);
        Set<T> instances = new HashSet<>();

        Class[] parameterClass = instantiateArray(parameters);
        for (Class<?> implementation : implementations) {
            Object o = implementation.getDeclaredConstructor(parameterClass).newInstance(parameters);
            if (tClass.isAssignableFrom(o.getClass())) {
                instances.add(tClass.cast(o));
            }
        }

        return instances;
    }

    private Class[] instantiateArray(Object[] parameters) {
        return Arrays.stream(parameters)
                .map(Object::getClass)
                .toArray(Class[]::new);
    }

    public Set<Class<?>> getImplementations(Class<?> tClass) {
        return modules.stream()
                .map(module -> module.getImplementations(tClass))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Set<Class<?>> getAllClasses() {
        return modules.stream()
                .map(Module::getClasses)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
