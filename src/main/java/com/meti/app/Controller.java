package com.meti.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public Logger getLogger() {
        return state.firstOfType(Logger.class).orElse(LoggerFactory.getLogger(getClass()));
    }
}
