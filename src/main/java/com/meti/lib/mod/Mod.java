package com.meti.lib.mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Mod {
    private final List<Class<?>> classes = new ArrayList<>();

    public Mod(Collection<Class<?>> classCollection) {
        this.classes.addAll(classCollection);
    }
}
