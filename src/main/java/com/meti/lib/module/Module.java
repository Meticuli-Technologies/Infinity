package com.meti.lib.module;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface Module {
    <T> Set<T> getInstances(Class<? extends T> instanceClass, List<?> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
