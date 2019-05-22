package com.meti.core;

import com.meti.core.load.PropertiesLoader;
import com.meti.core.state.InfinityState;
import com.meti.core.state.StateImpl;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Infinity {
    private final StateImpl stateImpl = new InfinityState();
    private final Logger logger = Logger.getLogger("Infinity");

    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting Infinity.");
        try {
            Properties properties = new PropertiesLoader().load();
            stateImpl.add(properties);
            stateImpl.add(logger);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start Infinity", e);
        }
    }

    public void stop() {
        logger.log(Level.INFO, "Stopping Infinity.");
    }
}