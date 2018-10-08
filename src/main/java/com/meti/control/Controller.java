package com.meti.control;

import com.meti.control.depend.Dependency;

import java.util.HashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public abstract class Controller extends HashMap<String, Dependency> {
    public <T extends Dependency> T getDependency(Class<? extends T> c) {
        return getDependency(c.getSimpleName());
    }

    public <T extends Dependency> T getDependency(String name) {
        //not much we can do here
        return (T) get(name);
    }

    public abstract String[] getDependencyNames();
}
