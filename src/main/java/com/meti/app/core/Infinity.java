package com.meti.app.core;

import com.meti.app.core.init.Initializer;
import com.meti.app.core.init.InitializerImpl;
import com.meti.app.core.start.Starter;
import com.meti.app.core.start.StarterImpl;
import com.meti.app.core.state.Toolkit;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Infinity {
    private final Logger logger = Logger.getLogger("Infinity");
    private final InitializerImpl initializerImpl;
    private final StarterImpl starterImpl;
    private Toolkit toolkit;

    public Infinity() {
        this.initializerImpl = new Initializer(logger);
        this.starterImpl = new Starter();
    }

    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting Infinity.");
        try {
            toolkit = initializerImpl.init(primaryStage);
            starterImpl.start(toolkit);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start Infinity", e);
        }
    }

    public void stop() {
        logger.log(Level.INFO, "Stopping Infinity.");
    }
}