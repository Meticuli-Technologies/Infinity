package com.meti.app;

import com.meti.lib.State;
import com.meti.lib.log.Console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
class InfinityState extends State {
    public Console getConsole() {
        return byClassToSingle(Console.class);
    }

    public ExecutorServiceManager getExecutorServiceManager() {
        return byClassToSingle(ExecutorServiceManager.class);
    }
}
