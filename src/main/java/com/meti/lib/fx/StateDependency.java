package com.meti.lib.fx;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/25/2018
 */
public class StateDependency extends Dependency {
    protected ControllerState controllerState;

    @Override
    public void load(ControllerState controllerState) {
        this.controllerState = controllerState;
    }
}
