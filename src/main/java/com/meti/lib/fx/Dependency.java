package com.meti.lib.fx;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/23/2018
 */
public abstract class Dependency implements Cloneable {
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public abstract void load(ControllerState controllerState);
}
