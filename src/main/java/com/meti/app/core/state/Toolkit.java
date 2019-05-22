package com.meti.app.core.state;

import com.meti.lib.fx.StageManagerImpl;
import com.meti.lib.mod.ModManagerImpl;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/21/2019
 */
public interface Toolkit {
    Logger getLogger();

    ModManagerImpl getModManager();

    Properties getProperties();

    StageManagerImpl getStageManager();

    StateImpl getState();
}
