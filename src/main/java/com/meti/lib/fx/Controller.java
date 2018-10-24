package com.meti.lib.fx;

import javax.crypto.spec.DESedeKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public abstract class Controller {
    public final Map<Class<? extends Dependency>, Dependency> dependencies = new HashMap<>();

    public <T extends Dependency> T getDependency(Class<T> dependencyClass){
        return dependencyClass.cast(dependencies.get(dependencyClass));
    }

    public abstract Set<Class<? extends Dependency>> getDependencyClasses();
}
