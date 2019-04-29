package com.meti.app.core.runtime;

import com.meti.app.ExecutorServiceManager;
import com.meti.app.io.InfinityServer;
import com.meti.lib.State;
import com.meti.lib.fx.StageManager;
import com.meti.lib.io.Server;
import com.meti.lib.log.Console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class InfinityState extends State {
    public Console getConsole() {
        return byClassToSingle(Console.class);
    }

    public ExecutorServiceManager getExecutorServiceManager() {
        return byClassToSingle(ExecutorServiceManager.class);
    }

    public InfinityServer getServer() {
        return byClassToSingle(InfinityServer.class);
    }

    public StageManager getStageManager() {
        return byClassToSingle(StageManager.class);
    }
}
