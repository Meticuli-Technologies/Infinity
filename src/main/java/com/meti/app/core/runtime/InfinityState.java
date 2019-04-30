package com.meti.app.core.runtime;

import com.meti.app.ExecutorServiceManager;
import com.meti.app.io.InfinityServer;
import com.meti.lib.State;
import com.meti.lib.fx.StageManager;
import com.meti.lib.io.channel.ObjectChannel;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.Querier;
import com.meti.lib.log.Console;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/23/2019
 */
public class InfinityState extends State {
    public ObjectChannel getChannel() {
        return byClassToSingle(ObjectChannel.class);
    }

    public ObjectSource<?> getClient() {
        return byClassToSingle(ObjectSource.class);
    }

    public Console getConsole() {
        return byClassToSingle(Console.class);
    }

    public Querier getQuerier() {
        return byClassToSingle(Querier.class);
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
