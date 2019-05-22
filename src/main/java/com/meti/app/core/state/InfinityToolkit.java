package com.meti.app.core.state;

import com.meti.app.client.ClientImpl;
import com.meti.app.server.InfinityServer;
import com.meti.lib.asset.AssetManagerImpl;
import com.meti.lib.javafx.StageManager;
import com.meti.lib.javafx.StageManagerImpl;
import com.meti.lib.mod.ModManagerImpl;
import com.meti.lib.net.ServerImpl;
import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.PortSourceSupplier;

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
    public AssetManagerImpl getAssetManager() {
        return state.getInstance(AssetManagerImpl.class);
    }

    @Override
    public ClientImpl getClient() {
        return state.getInstance(ClientImpl.class);
    }

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
    public ServerImpl<CompoundSource<?, ?>, PortSourceSupplier> getServer() {
        return state.getInstance(InfinityServer.class);
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