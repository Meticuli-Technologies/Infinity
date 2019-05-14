package com.meti.core;

import com.meti.control.Menu;
import com.meti.fx.StageManager;
import com.meti.net.source.URLSource;
import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.Injector;
import com.meti.module.InfinityModuleManager;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Main extends Application {
    private final ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool());
    private final InfinityModuleManager moduleManager = new InfinityModuleManager();
    private final StageManager stageManager = new StageManager();
    private final List<Closeable> closeables = new ArrayList<>();
    private final Logger logger = Logger.getLogger("Infinity");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting application.");
        stageManager.addStage(primaryStage);

        try {
            Injector injector = new Injector(logger, executorServiceManager, stageManager, moduleManager);
            Parent parent = injector.load(URLSource.of("/com/meti/Menu.fxml"));
            Stage stage = stageManager.getPrimaryStage();
            stage.setScene(new Scene(parent));
            stage.show();

            Menu menu = injector.getController();
            menu.getServerLoader().setOnConstructed(closeables::add);
            menu.getClientLoader().setOnConstructed(closeables::add);
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
