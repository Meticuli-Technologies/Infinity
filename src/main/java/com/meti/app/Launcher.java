package com.meti.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/7/2019
 */
class Launcher {
    final Method method;

    public Launcher(Method method) {
        this.method = method;
    }

    public void invoke(Class<?> mainClass, String[] args) throws IllegalAccessException, InvocationTargetException {
        method.invoke(null, mainClass, args);
    }
}
