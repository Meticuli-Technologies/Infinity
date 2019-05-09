package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Main extends Application {
    private final ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool());
    private final Logger logger = Logger.getLogger("Infinity");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting application.");

        try {
            primaryStage.setScene(Injector.loadAsScene(URLSource.of("/com/meti/Menu.fxml"), logger, executorServiceManager));
            primaryStage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start Infinity: " + e);
        }
    }
}
