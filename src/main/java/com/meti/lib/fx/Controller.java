package com.meti.lib.fx;

import com.meti.lib.state.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Controller<S extends State> {
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        if (this.state == null) {
            this.state = state;
        } else {
            throw new IllegalArgumentException("State has already been set");
        }
    }
}
