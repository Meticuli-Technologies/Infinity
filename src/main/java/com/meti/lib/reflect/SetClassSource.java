package com.meti.lib.reflect;

import com.meti.lib.util.CollectionUtil;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class SetClassSource implements ClassSource {
    private final Set<Class<?>> classes;

    public SetClassSource(Set<Class<?>> classes) {
        this.classes = classes;
    }

    @Override
    public Class<?> byName(String name) {
        Set<Class<?>> foundClasses = classes.stream()
                .filter(aClass -> aClass.getSimpleName().equals(name))
                .collect(Collectors.toSet());
        if (foundClasses.isEmpty()) {
            throw new IllegalArgumentException("No classes found for name " + name);
        }
        return CollectionUtil.toSingle(foundClasses);
    }

    @Override
    public Set<Class<?>> bySuper(Class<?> superClass) {
        return classes.stream()
                .filter(superClass::isAssignableFrom)
                .collect(Collectors.toSet());
    }
}
