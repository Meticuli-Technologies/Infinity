package com.meti.lib.reflect;

import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface ClassSource {
    ClassLoader getClassLoader();

    default Set<Class<?>> bySuper(String superClassName) {
        Optional<Class<?>> superClass = byName(superClassName);
        if (superClass.isPresent()) {
            return bySuper(superClass.get());
        } else {
            throw new IllegalArgumentException("Superclass not found for name " + superClassName);
        }
    }

    Optional<Class<?>> byName(String name);

    Set<Class<?>> bySuper(Class<?> superClass);
}
