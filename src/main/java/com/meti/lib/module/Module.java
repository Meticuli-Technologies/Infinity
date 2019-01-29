package com.meti.lib.module;

import com.meti.lib.reflect.ClassSource;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class Module {
    public final String name;
    private final ClassSource source;

    public Module(String name, ClassSource source) {
        this.name = name;
        this.source = source;
    }
}
