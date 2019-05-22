package com.meti.app.core;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class Main extends Application  {
    private final InfinityImpl infinityImpl = new Infinity();

    @Override
    public void start(Stage primaryStage) {
        infinityImpl.start(primaryStage);
    }

    @Override
    public void stop() {
        infinityImpl.stop();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
