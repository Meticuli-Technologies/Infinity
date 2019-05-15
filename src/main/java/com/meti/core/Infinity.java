package com.meti.core;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;

import static com.meti.util.ExceptionUtil.stackTraceString;

class Infinity {
    private final InfinitySystem system = new InfinitySystem("Infinity", Executors.newCachedThreadPool());
    private final List<Closeable> closeables = new ArrayList<>();

    private final InfinityStopper infinityStopper = new InfinityStopper(system);
    private final InfinityStarter infinityStarter = new InfinityStarter(system);

    void start(Stage primaryStage) {
        try {
            infinityStarter.startImpl(primaryStage);
        } catch (Exception e) {
            system.getLogger().log(Level.SEVERE, "Failed to start Infinity: " + stackTraceString(e));
            Platform.exit();
        }
    }

    void stop() {
        infinityStopper.stop(closeables);
    }
}