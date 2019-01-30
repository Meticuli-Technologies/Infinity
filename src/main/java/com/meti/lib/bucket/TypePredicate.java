package com.meti.lib.bucket;

import java.util.Collections;
import java.util.Set;
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
    public Set<Class<?>> getParameters() {
        return Collections.singleton(typeClass);
    }

    @Override
    public boolean test(Object object) {
        return typeClass.isAssignableFrom(object.getClass());
    }
}
