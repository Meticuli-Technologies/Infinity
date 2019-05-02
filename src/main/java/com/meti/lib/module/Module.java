package com.meti.lib.module;

import java.util.stream.Stream;

abstract class Module {
    public final String name;

    Module(String name) {
        this.name = name;
    }

    public abstract <T> Stream<Class<?>> getImplementations(Class<T> superClass);
}