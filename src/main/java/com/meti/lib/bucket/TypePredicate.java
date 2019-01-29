package com.meti.lib.bucket;

import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class TypePredicate<T> implements Parameterized<Class<?>>, Predicate<Object> {
    private final Class<T> typeClass;

    public TypePredicate(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    @Override
    public Class<?>[] getParameters() {
        return new Class[]{typeClass};
    }

    @Override
    public boolean test(Object object) {
        return typeClass.isAssignableFrom(object.getClass());
    }
}
