package com.meti.core;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class State implements StateImpl {
    private final Set<Object> components = new HashSet<>();

    @Override
    public void add(Object object) {
        if (components.contains(object)) {
            //TODO: internationalize
            throw new IllegalArgumentException("Already contains " + object);
        }
        components.add(object);
    }
}
