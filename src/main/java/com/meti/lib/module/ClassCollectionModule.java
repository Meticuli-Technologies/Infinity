package com.meti.lib.module;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.meti.lib.reflect.Instantiator.genericInstanceSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class ClassCollectionModule implements Module {
    private final Collection<Class<?>> classes = new HashSet<>();

    public ClassCollectionModule(Collection<Class<?>> initialClasses) {
        this.classes.addAll(initialClasses);
    }

    @Override
    public <T> Set<T> getInstances(Class<? extends T> superClass, List<?> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<T> set = new HashSet<>();
        for (Class<?> specificClass : classes) {
            if (superClass.isAssignableFrom(specificClass)) {
                genericInstanceSet(dependencies, specificClass)
                        .stream()
                        .filter(superClass::isInstance)
                        .map(superClass::cast)
                        .forEach(set::add);
            }
        }
        return set;
    }
}
