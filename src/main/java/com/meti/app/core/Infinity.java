package com.meti.app.core;

import com.meti.app.core.init.InfinityInitializer;
import com.meti.app.core.state.InfinityState;
import com.meti.app.core.state.StateImpl;
import com.meti.app.core.start.InfinityStarter;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Infinity {
    private final StateImpl stateImpl = new InfinityState();
    private final Logger logger = Logger.getLogger("Infinity");

    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting Infinity.");
        try {
            new InfinityInitializer(this.stateImpl, this.logger).initializer(primaryStage);
            new InfinityStarter(this.stateImpl).start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start Infinity", e);
        }
    }

    public void stop() {
        logger.log(Level.INFO, "Stopping Infinity.");
    }
}