package com.meti.lib.convert;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public class ClassConverter<T> implements Converter<T> {
    public final Class<T> tClass;

    public ClassConverter(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T apply(Object o) {
        return tClass.cast(o);
    }

    @Override
    public boolean test(Object o) {
        return tClass.isAssignableFrom(o.getClass());
    }
}
