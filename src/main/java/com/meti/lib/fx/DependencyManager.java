package com.meti.lib.fx;

import com.meti.lib.manage.Manager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public class DependencyManager extends Manager<Class<? extends Dependency>, Dependency> {
    public void addDependency(Dependency dependency) {
        put(dependency.getClass(), dependency);
    }

    public <T extends Dependency> T getDependencyOfClass(Class<T> c) {
        return c.cast(get(c));
    }
}
