package com.meti.lib.fx.state;

import com.meti.lib.State;

public abstract class StateController {
    public StateController(State state){
        buildFields(state);
    }

    protected abstract void buildFields(State state);
}
