package com.meti.lib.convert;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public class ClassConverter<T> extends Converter<Object, T> {
    private final Class<T> typeClass;
    private final boolean useSubClass;

    public ClassConverter(Class<T> typeClass) {
        this(typeClass, true);
    }

    public ClassConverter(Class<T> typeClass, boolean useSubClass) {
        this.typeClass = typeClass;
        this.useSubClass = useSubClass;
    }

    @Override
    public T apply(Object o) {
        return typeClass.cast(o);
    }

    @Override
    public boolean test(Object o) {
        if (useSubClass) {
            return typeClass.isAssignableFrom(o.getClass());
        } else {
            return typeClass.equals(o.getClass());
        }
    }
}
