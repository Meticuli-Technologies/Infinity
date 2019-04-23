package com.meti.app;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    private final InfinityInitializer infinityInitializer = new InfinityInitializer();
    private final InfinityStarter infinityStarter = new InfinityStarter();
    private final InfinityState mainState = new InfinityState();

    @Override
    public void start(Stage primaryStage) {
        infinityInitializer.init(mainState, primaryStage);
        infinityStarter.startImpl(primaryStage, mainState);
    }

    private void logTaskString() {
        mainState.getExecutorServiceManager().getTaskString().ifPresentOrElse(s -> mainState.getConsole().log(Level.SEVERE, s),
                () -> mainState.getConsole().log(Level.INFO, "The ExecutorService has been shutdown with no tasks awaiting execution.")
        );
    }

    @Override
    public void stop() {
        try {
            stopImpl();
        } catch (Exception e) {
            mainState.getConsole().log(Level.SEVERE, e);
        }
    }

    private void stopImpl() throws Exception {
        terminateExecutor();
    }

    private void terminateExecutor() throws Exception {
        logTaskString();
        mainState.getExecutorServiceManager().checkTerminated();
    }

    private void setAndShowScene(Stage primaryStage, Scene scene) {
        infinityStarter.setAndShowScene(primaryStage, scene);
    }
}
