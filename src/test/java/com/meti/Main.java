package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/4/2019
 */
public class Main extends Application {
    static Infinity infinity = new Infinity();
    static Main instance;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        infinity.start(primaryStage);
    }

    @Override
    public void stop() {
        infinity.stop();
        instance = null;
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class Infinity {
        public void start(Stage primaryStage) {
        }

        public void stop() {
        }
    }
}
