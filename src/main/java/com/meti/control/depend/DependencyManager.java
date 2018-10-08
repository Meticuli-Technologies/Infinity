package com.meti.control.depend;

import java.util.HashMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public class DependencyManager extends HashMap<String, DependencyFactory> {
    {
        addDependencyFactory(new DependencyFactory("BlankDependency") {
            @Override
            public Dependency create() {
                return new BlankDependency(name);
            }
        });
    }

    public void addDependencyFactory(DependencyFactory dependencyFactory){
        put(dependencyFactory.name, dependencyFactory);
    }
}
