package com.meti.app.core;

import com.meti.app.core.init.Initializer;
import com.meti.app.core.init.InitializerImpl;
import com.meti.app.core.start.Starter;
import com.meti.app.core.start.StarterImpl;
import com.meti.app.core.state.Toolkit;
import com.meti.app.core.stop.Stopper;
import com.meti.app.core.stop.StopperImpl;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Infinity extends InfinityImpl {
    private final Logger logger = Logger.getLogger("Infinity");
    private final InitializerImpl initializerImpl;
    private final StarterImpl starterImpl;
    private final StopperImpl stopper;
    private Toolkit toolkit;

    public Infinity() {
        this.initializerImpl = new Initializer(logger);
        this.starterImpl = new Starter();
        this.stopper = new Stopper();
    }

    @Override
    public void start(Stage primaryStage) {
        logger.log(Level.INFO, "Starting Infinity.");
        try {
            toolkit = initializerImpl.init(primaryStage);
            starterImpl.start(toolkit);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to start Infinity", e);
        }
    }

    @Override
    public void stop() {
        logger.log(Level.INFO, "Stopping Infinity.");
        stopper.stop(toolkit);
    }
}