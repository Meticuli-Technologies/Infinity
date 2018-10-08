package com.meti.control.depend;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/6/2018
 */
public class Dependency {
    public final String name;

    public Dependency(){
        this.name = getClass().getSimpleName();
    }

    public Dependency(String name) {
        this.name = name;
    }
}
