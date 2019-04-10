package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.fx.Controller;
import com.meti.lib.log.LoggerConsole;
import javafx.stage.Stage;

class InfinityController extends Controller {
    final LoggerConsole console;
    final TryableFactory factory;

    public InfinityController(State state, Stage stage) {
        super(state, stage);

        this.console = state.byClass(LoggerConsole.class).findAny().orElseThrow();
        this.factory = state.byClass(TryableFactory.class).findAny().orElseThrow();
    }
}
