package com.meti.app.core.state;

import com.meti.lib.fx.StageManager;
import com.meti.lib.mod.ModManagerImpl;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class InfinityState extends State {
    public Logger getLogger() {
        return getInstance(Logger.class);
    }

    public ModManagerImpl getModManager() {
        return getInstance(ModManagerImpl.class);
    }

    public Properties getProperties() {
        return getInstance(Properties.class);
    }

    public StageManager getStageManager() {
        return getInstance(StageManager.class);
    }
}