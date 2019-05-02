package com.meti.app.control.util;

import com.meti.app.ExecutorServiceManager;
import com.meti.app.core.runtime.InfinityState;
import com.meti.lib.fx.ControllerLoader;
import com.meti.lib.fx.StageManager;
import com.meti.lib.fx.state.StateController;
import com.meti.lib.util.collect.State;
import com.meti.lib.util.log.Console;

import java.io.IOException;
import java.net.URL;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class InfinityController extends StateController {
    public ExecutorServiceManager serviceManager;
    public InfinityState state;
    protected Console console;

    protected StageManager stageManager;

    protected InfinityController(State state) {
        super(state);
    }

    @Override
    public void buildFields(State state) {
        if (state instanceof InfinityState) {
            this.state = buildFields((InfinityState) state);
        } else {
            throw new IllegalArgumentException(InfinityState.class.getSimpleName() + " is not assignable to " + state.getClass());
        }
    }

    private InfinityState buildFields(InfinityState state) {
        serviceManager = state.getExecutorServiceManager();
        stageManager = state.getStageManager();
        console = state.getConsole();
        return state;
    }

    protected void onto(URL resource, int index) throws IOException {
        stageManager.setRootToStage(ControllerLoader.loadRoot(resource, state), index)
                .show();
    }
}
