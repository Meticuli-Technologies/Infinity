package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.fx.StateControllerLoader;
import com.meti.lib.log.Console;
import com.meti.lib.log.LoggerConsole;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    private static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);
    private final ExecutorServiceManager executorServiceManager;
    private final Console console;
    private final State mainState;

    Infinity() {
        this.executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool(), AWAIT_TERMINATION);
        this.console = new LoggerConsole();
        this.mainState = new State();
    }

    @Override
    public void start(Stage primaryStage) {
        mainState.add(primaryStage);

        try {
            Scene scene = new Scene(StateControllerLoader.load(getClass().getResource("/com/meti/app/control/Menu.fxml")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            console.log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
        try {
            stopImpl();
        } catch (Exception e) {
            console.log(Level.SEVERE, e);
        }
    }

    private void stopImpl() throws Exception {
        terminateExecutor();
    }

    private void terminateExecutor() throws Exception {
        logTaskString();
        executorServiceManager.checkTerminated();
    }

    private void logTaskString() {
        executorServiceManager.getTaskString().ifPresentOrElse(
                s -> console.log(Level.SEVERE, s),
                () -> console.log(Level.INFO, "The ExecutorService has been shutdown with no tasks awaiting execution.")
        );
    }
}
