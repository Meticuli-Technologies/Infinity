package com.meti;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
public class Main extends Application {
    static boolean launched;
    static InfinityContext context = new Infinity();

    @Override
    public void start(Stage primaryStage) {
        launched = true;
        context.start(primaryStage);
    }

    @Override
    public void stop() {
        context.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
