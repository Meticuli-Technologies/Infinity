package com.meti.lib.reflect;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface ClassSource {
    Class<?> byName(String name);

    default Set<Class<?>> bySuper(String superClassName) {
        return bySuper(byName(superClassName));
    }

    Set<Class<?>> bySuper(Class<?> superClass);
}
