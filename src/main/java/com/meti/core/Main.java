package com.meti.core;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Main extends Application {
    private final Infinity infinity = new Infinity();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        infinity.start(primaryStage);
    }

    @Override
    public void stop() {
        infinity.stop();
    }
}
