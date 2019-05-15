package com.meti.control;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.fx.StageManager;
import com.meti.module.InfinityModuleManager;
import com.meti.module.ModuleManager;

import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/14/2019
 */
public class InfinityController {
    protected final Logger logger;
    private final ExecutorServiceManager executorServiceManager;
    protected final StageManager stageManager;
    protected final ModuleManager moduleManager;

    protected InfinityController(Logger logger, ExecutorServiceManager executorServiceManager, StageManager stageManager, InfinityModuleManager moduleManager) {
        this.logger = logger;
        this.executorServiceManager = executorServiceManager;
        this.stageManager = stageManager;
        this.moduleManager = moduleManager;
    }

    ExecutorServiceManager getExecutorServiceManager() {
        return executorServiceManager;
    }

    Logger getLogger() {
        return logger;
    }

    ModuleManager getModuleManager() {
        return moduleManager;
    }

    StageManager getStageManager() {
        return stageManager;
    }
}
