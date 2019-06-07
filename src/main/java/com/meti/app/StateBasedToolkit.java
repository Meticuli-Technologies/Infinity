package com.meti.app;

import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.collect.State;
import com.meti.lib.javafx.StageManager;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public class StateBasedToolkit implements Toolkit {
    private final State state;

    public StateBasedToolkit(State state) {
        this.state = state;
    }

    @Override
    public Client getClient() {
        return state.singleByClass(Client.class).orElseThrow();
    }

    @Override
    public Server getServer() {
        return state.singleByClass(Server.class).orElseThrow();
    }

    @Override
    public StageManager getStageManager() {
        return state.singleByClass(StageManager.class).orElseThrow();
    }

    @Override
    public AssetManager getAssetManager() {
        return state.singleByClass(AssetManager.class).orElseThrow();
    }
}
