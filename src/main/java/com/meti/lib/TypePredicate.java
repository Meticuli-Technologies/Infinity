package com.meti.lib;

import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class TypePredicate<T> implements Predicate<Object> {
    private Class<T> tClass;

    public TypePredicate(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public boolean test(Object o) {
        return tClass.isAssignableFrom(o.getClass());
    }
}
