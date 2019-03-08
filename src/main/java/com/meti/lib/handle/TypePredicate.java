package com.meti.lib.handle;

import java.util.function.Predicate;

public class TypePredicate<T> implements Predicate<Object> {
    private final Class<T> tClass;
    private boolean equal = false;

    public TypePredicate(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public Class<T> gettClass() {
        return tClass;
    }

    @Override
    public boolean test(Object o) {
        if (isEqual()) {
            return tClass.equals(o.getClass());
        } else {
            return tClass.isAssignableFrom(o.getClass());
        }
    }

    public boolean isEqual() {
        return equal;
    }
}
