package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.fx.StageManager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class StateToolkit implements Toolkit {
    private final State state;

    public StateToolkit(State state) {
        this.state = state;
    }

    @Override
    public StageManager getStageManager() {
        return state.singleByClass(StageManager.class).orElseThrow();
    }
}
