package com.meti.lib.collect;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
class MapBinding<T> {
    public final Class<T> tClass;
    public final ArrayList<T> content = new ArrayList<>();

    public MapBinding(Class<T> initialClass, Object initial) {
        this(initialClass);
        add(initial);
    }

    private MapBinding(Class<T> tClass) {
        this.tClass = tClass;
    }

    public void add(Object object) {
        if (object.getClass().isAssignableFrom(tClass)) {
            content.add(tClass.cast(object));
        } else {
            throw new IllegalArgumentException("Invalid type " + object.getClass() + ", should be type " + tClass.getSimpleName());
        }
    }
}
