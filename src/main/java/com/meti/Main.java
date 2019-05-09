package com.meti;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Main extends Application {
    private final ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool());
    private final List<Closeable> closeables = new ArrayList<>();
    private final Logger logger = Logger.getLogger("Infinity");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting application.");

        try {
            Injector injector = new Injector(logger, executorServiceManager);
            Parent parent = injector.load(URLSource.of("/com/meti/Menu.fxml"));
            primaryStage.setScene(new Scene(parent));
            primaryStage.show();

            Menu menu = injector.getController();
            menu.setOnServerConstructed(closeables::add);
            menu.setOnClientConstructed(closeables::add);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start Infinity: " + e);
        }
    }

    @Override
    public void stop() {
        close();
        terminate();
    }

    private void terminate() {
        try {
            logger.log(Level.INFO, "Finalizing futures.");
            executorServiceManager.finalizeFutures()
                    .values()
                    .stream()
                    .filter(o -> o instanceof Throwable)
                    .forEach(o -> logger.log(Level.WARNING, o.toString()));
            logger.log(Level.INFO, "Stopping executor service.");
            executorServiceManager.stop(Duration.ofSeconds(1));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void close() {
        logger.log(Level.INFO, "Closing closeables.");
        for (Closeable closeable : closeables) {
            try {
                closeable.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    }
}
