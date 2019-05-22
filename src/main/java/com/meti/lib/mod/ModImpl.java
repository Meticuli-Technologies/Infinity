package com.meti.lib.mod;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ModImpl {
    boolean containsClass(Class<?> clazz);

    boolean containsImplementationClass(Class<?> clazz);

    <T> Set<T> instantiate(Class<? extends T> tClass, List<Object> dependencies) throws IllegalAccessException, InstantiationException, InvocationTargetException;
}
