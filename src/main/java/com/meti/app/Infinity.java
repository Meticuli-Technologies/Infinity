package com.meti.app;

import com.meti.lib.State;
import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Infinity implements InfinityImpl {
    private final State mainState;

    public Infinity(State mainState) {
        this.mainState = mainState;
    }

    @Override
    public void start(Stage primaryStage) {
        mainState.add(primaryStage);
    }

    @Override
    public void stop() {

    }
}
