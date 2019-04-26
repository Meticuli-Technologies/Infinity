package com.meti.app.core.runtime;

import com.meti.app.ExecutorServiceManager;
import com.meti.lib.State;
import com.meti.lib.fx.StageManager;
import com.meti.lib.log.LoggerConsole;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.concurrent.Executors;

class InfinityInitializer {
    private static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);

    void init(State state, Stage primaryStage) {
        state.add(initStageManager(primaryStage));
        state.add(initExecutorServiceManager());
        state.add(initLoggerConsole());
    }

    private StageManager initStageManager(Stage primaryStage) {
        StageManager stageManager = new StageManager();
        stageManager.add(primaryStage);
        return stageManager;
    }

    private ExecutorServiceManager initExecutorServiceManager() {
        return new ExecutorServiceManager(Executors.newCachedThreadPool(), AWAIT_TERMINATION);
    }

    private LoggerConsole initLoggerConsole() {
        return new LoggerConsole();
    }
}