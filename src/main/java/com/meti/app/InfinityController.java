package com.meti.app;

import com.meti.lib.collect.State;
import com.meti.lib.collect.tryable.TryableFactory;
import com.meti.lib.fx.Controller;
import com.meti.lib.fx.ControllerLoaderFunction;
import com.meti.lib.log.LoggerConsole;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Optional;

class InfinityController extends Controller {
    final LoggerConsole console;
    final TryableFactory<?> factory;

    public InfinityController(State state, Stage stage) {
        super(state, stage);

        this.console = state.byClass(LoggerConsole.class).findAny().orElseThrow();
        this.factory = state.byClass(TryableFactory.class).findAny().orElseThrow();
    }

    public <T> T onto(InputStream inputStream) {
        return onto(inputStream, factory.apply(new ControllerLoaderFunction<T>(state, stage)).andThen(Optional::orElseThrow));
    }
}
