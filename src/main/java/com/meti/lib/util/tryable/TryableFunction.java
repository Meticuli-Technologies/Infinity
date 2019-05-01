package com.meti.lib.util.tryable;

import java.lang.reflect.InvocationTargetException;

public interface TryableFunction<T, R> extends Tryable {
    R apply(T t) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, Exception;
}
