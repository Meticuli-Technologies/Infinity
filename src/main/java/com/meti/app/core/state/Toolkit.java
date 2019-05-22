package com.meti.app.core.state;

import com.meti.lib.javafx.StageManagerImpl;
import com.meti.lib.mod.ModManagerImpl;
import com.meti.lib.net.ServerImpl;
import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

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

    ServerImpl<CompoundSource<?, ?>, SourceSupplier<CompoundSource<?, ?>>> getServer();

    StageManagerImpl getStageManager();

    StateImpl getState();
}
