package com.meti.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/com/meti/app/Menu.fxml"))));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
