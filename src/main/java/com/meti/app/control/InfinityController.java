package com.meti.app.control;

import com.meti.lib.State;
import com.meti.lib.fx.Controller;

import java.util.concurrent.ExecutorService;

public class InfinityController extends Controller {
    public InfinityController(State state) {
        super(state);
    }

    public ExecutorService getExecutorService() {
        return state.byClassToSingle(ExecutorService.class).orElseThrow();
    }
}
