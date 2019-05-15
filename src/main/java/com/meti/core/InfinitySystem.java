package com.meti.core;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.StageManager;
import com.meti.module.ModuleManager;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class InfinitySystem {
    private final StageManager stageManager = new StageManager();
    private final ModuleManager moduleManager = new ModuleManager();
    private final ExecutorServiceManager executorServiceManager;
    private final Logger logger;

    public InfinitySystem(String name, ExecutorService service) {
        this.executorServiceManager = new ExecutorServiceManager(service);
        this.logger = Logger.getLogger(name);
    }

    public ExecutorServiceManager getExecutorServiceManager() {
        return executorServiceManager;
    }

    public Logger getLogger() {
        return logger;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }
}
