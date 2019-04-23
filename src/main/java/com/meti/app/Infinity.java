package com.meti.app;

import com.meti.lib.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    private static final Duration AWAIT_TERMINATION = Duration.ofSeconds(1);
    private final ExecutorServiceManager executorServiceManager;
    private final State mainState;

    Infinity() {
        this.executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool(), AWAIT_TERMINATION);
        this.mainState = new State();
    }

    @Override
    public void start(Stage primaryStage) {
        mainState.add(primaryStage);

        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/control/Menu.fxml")));
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
