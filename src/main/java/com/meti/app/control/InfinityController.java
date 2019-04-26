package com.meti.app.control;

import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.State;
import com.meti.lib.fx.StateController;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class InfinityController extends StateController {
    final InfinityState state;

    public InfinityController(State state) {
        super(state);

        if (state instanceof InfinityState) {
            this.state = ((InfinityState) state);
        } else {
            throw new IllegalArgumentException(InfinityState.class.getSimpleName() + " is not assignable to " + state.getClass());
        }
    }
}
