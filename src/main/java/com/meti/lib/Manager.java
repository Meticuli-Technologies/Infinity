package com.meti.lib;

import java.util.HashSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
public abstract class Manager<T> extends HashSet<T> {
    public T allocate(){
        T t = create();
        add(t);
        return t;
    }

    protected abstract T create();
}
