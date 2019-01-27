package com.meti.app;

import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Infinity {
    private final InfinityState state = new InfinityState();

    public void start(Stage primaryStage) {
        state.buckets.add(primaryStage);
    }

    public void stop() {

    }
}
