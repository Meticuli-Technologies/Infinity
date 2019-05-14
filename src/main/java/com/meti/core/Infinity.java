package com.meti.core;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import javafx.stage.Stage;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

class Infinity {
    private final ExecutorServiceManager executorServiceManager = new ExecutorServiceManager(Executors.newCachedThreadPool());
    private final InfinityModuleManager moduleManager = new InfinityModuleManager();
    private final StageManager stageManager = new StageManager();
    private final List<Closeable> closeables = new ArrayList<>();
    private final Logger logger = Logger.getLogger("Infinity");
    private final InfinityStopper infinityStopper = new InfinityStopper(this);
    private final InfinityStarter infinityStarter = new InfinityStarter(this);

    public List<Closeable> getCloseables() {
        return closeables;
    }

    public ExecutorServiceManager getExecutorServiceManager() {
        return executorServiceManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public InfinityModuleManager getModuleManager() {
        return moduleManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    void start(Stage primaryStage) {
        try {
            infinityStarter.startImpl(primaryStage);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to start Infinity: " + e);
        }
    }

    void stop() {
        infinityStopper.stop();
    }
}