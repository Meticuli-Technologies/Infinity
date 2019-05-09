package com.meti;

import java.util.Set;

public abstract class Module extends Asset {

    public Module(String name) {
        super(name);
    }

    public abstract Set<Class<?>> getClasses();

    public abstract <T> Set<Class<?>> getImplementations(Class<T> tClass);
}
