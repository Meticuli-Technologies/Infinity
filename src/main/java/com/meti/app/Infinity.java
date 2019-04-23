package com.meti.app;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    private final InfinityInitializer infinityInitializer = new InfinityInitializer();
    private final InfinityStarter infinityStarter = new InfinityStarter();
    private final InfinityStopper infinityStopper = new InfinityStopper();
    private final InfinityState mainState = new InfinityState();

    @Override
    public void start(Stage primaryStage) {
        try {
            infinityInitializer.init(mainState, primaryStage);
            infinityStarter.startImpl(primaryStage, mainState);
        } catch (IOException e) {
            mainState.getConsole().log(Level.SEVERE, e);
        }
    }

    @Override
    public void stop() {
        try {
            infinityStopper.stopImpl(mainState);
        } catch (Exception e) {
            mainState.getConsole().log(Level.SEVERE, e);
        }
    }
}
