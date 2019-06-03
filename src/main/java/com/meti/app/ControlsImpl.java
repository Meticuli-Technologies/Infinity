package com.meti.app;

import com.meti.lib.collect.State;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class ControlsImpl implements Controls {
    private final State state;
    private final Toolkit toolkit;

    public ControlsImpl(State state, Toolkit toolkit) {
        this.state = state;
        this.toolkit = toolkit;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Toolkit getToolkit() {
        return toolkit;
    }
}
