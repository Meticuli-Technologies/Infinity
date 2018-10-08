package com.meti.control.depend;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public abstract class DependencyFactory {
    public final String name;

    public DependencyFactory(String name) {
        this.name = name;
    }

    public abstract Dependency create();
}
