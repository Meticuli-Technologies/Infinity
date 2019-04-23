package com.meti.app;

import com.meti.lib.State;
import javafx.stage.Stage;

import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    public static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);
    private final ExecutorServiceManager executorServiceManager;
    private final State mainState;

    Infinity() {
        this.executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool(), AWAIT_TERMINATION);
        this.mainState = new State();
    }

    @Override
    public void start(Stage primaryStage) {
        mainState.add(primaryStage);
    }

    @Override
    public void stop() {
        try {
            stopImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopImpl() throws Exception {
        terminateExecutor();
    }

    private void terminateExecutor() throws Exception {
        String taskString = executorServiceManager.getTaskString();
        System.out.println(taskString);
        executorServiceManager.checkTerminated();
    }
}
