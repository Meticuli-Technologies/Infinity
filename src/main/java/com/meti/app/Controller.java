package com.meti.app;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Controller {
    protected ControllerState state;

    public void setState(ControllerState state) {
        this.state = state;
    }
}
