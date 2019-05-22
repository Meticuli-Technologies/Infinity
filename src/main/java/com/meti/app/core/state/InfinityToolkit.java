package com.meti.app.core.state;

import com.meti.lib.javafx.StageManager;
import com.meti.lib.javafx.StageManagerImpl;
import com.meti.lib.mod.ModManagerImpl;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public class InfinityToolkit implements Toolkit {
    private final StateImpl state = new State();

    @Override
    public Logger getLogger() {
        return state.getInstance(Logger.class);
    }

    @Override
    public ModManagerImpl getModManager() {
        return state.getInstance(ModManagerImpl.class);
    }

    @Override
    public Properties getProperties() {
        return state.getInstance(Properties.class);
    }

    @Override
    public StageManagerImpl getStageManager() {
        return state.getInstance(StageManager.class);
    }

    @Override
    public StateImpl getState() {
        return state;
    }
}