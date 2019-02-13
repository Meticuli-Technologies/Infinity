package com.meti;

import javafx.stage.Stage;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/13/2019
 */
public class Infinity implements InfinityContext {
    InfinityInitializer initializer = new InfinityInitializerImpl();
    boolean initialized = false;

    @Override
    public void start(Stage primaryStage) {
        /*
        The intended purpose of this if statement
        is to disconnect initialization code from FX code.
        This has to be done because we want to test code
        even though it's part of the main launching process.
        We shouldn't test code on the FX thread if we don't need to.
         */
        if (!initialized) {
            initialized = initializer.init();
        }
    }
    @Override
    public void stop() {
    }

    interface InfinityInitializer {
        boolean init();
    }

    private class InfinityInitializerImpl implements InfinityInitializer {
        @Override
        public boolean init() {
            return true;
        }
    }
}

