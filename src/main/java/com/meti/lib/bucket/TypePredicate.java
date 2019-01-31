package com.meti.lib.bucket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class TypePredicate<T> extends ParameterizedPredicate<Object, Class<T>> {
    public TypePredicate(Class<T> typeClass) {
        super(typeClass);
    }

    @Override
    public boolean test(Object object) {
        return parameters.get(0).isAssignableFrom(object.getClass());
    }
}
