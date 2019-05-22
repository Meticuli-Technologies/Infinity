package com.meti.lib.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface InstantiatorImpl {
    List<Object> instantiate(Class<?> instantiatee, List<Object> dependencies) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    <T> List<T> instantiateGeneric(Class<T> tClass, List<Object> dependencies) throws IllegalAccessException, InstantiationException, InvocationTargetException;
}
