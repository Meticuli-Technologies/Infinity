package com.meti.app.core;

import javafx.stage.Stage;

import java.io.Closeable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Infinity {
    private final InfinityState state = new InfinityState();

    public void start(Stage primaryStage){
        state.add(primaryStage);
    }

    public void stop(){
        state.search(Closeable.class);
    }
}
