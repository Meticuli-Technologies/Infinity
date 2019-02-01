package com.meti.lib.reflect;

import com.meti.lib.util.CollectionUtil;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class SetClassSource implements ClassSource {
    private final Set<Class<?>> classes;
    private final ClassLoader classLoader;

    public SetClassSource(Set<Class<?>> classes, ClassLoader classLoader) {
        this.classes = classes;
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    @Override
    public Optional<Class<?>> byName(String name) {
        Set<Class<?>> foundClasses = classes.stream()
                .filter(aClass -> aClass.getName().equals(name))
                .collect(Collectors.toSet());

        if (foundClasses.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(CollectionUtil.toSingle(foundClasses));
    }

    @Override
    public Set<Class<?>> bySuper(Class<?> superClass) {
        return classes.stream()
                .filter(superClass::isAssignableFrom)
                .collect(Collectors.toSet());
    }
}
