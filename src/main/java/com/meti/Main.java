package com.meti;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Main extends Application {
    private final Logger logger = Logger.getLogger("Infinity");

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting application.");

        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/Menu.fxml"))));
            primaryStage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start Infinity: " + e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
