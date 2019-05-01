package com.meti.lib.module;

import java.util.stream.Stream;

public abstract class Module {
    public final String name;

    public Module(String name) {
        this.name = name;
    }

    public abstract <T> Stream<Class<?>> getImplementations(Class<T> superClass);
}