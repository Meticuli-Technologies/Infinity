package com.meti.app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private final Infinity infinity = new Infinity();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        infinity.start(primaryStage);
    }
}
