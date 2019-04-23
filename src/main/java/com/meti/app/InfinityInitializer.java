package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.log.LoggerConsole;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.concurrent.Executors;

class InfinityInitializer {
    private static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);

    void init(State state, Stage primaryStage) {
        ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool(), AWAIT_TERMINATION);
        LoggerConsole loggerConsole = new LoggerConsole();

        state.add(executorServiceManager);
        state.add(loggerConsole);
        state.add(primaryStage);
    }
}