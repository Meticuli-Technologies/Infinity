package com.meti.app;

import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.javafx.StageManager;
import com.meti.lib.module.ModuleManager;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public interface Toolkit {
    Client getClient();

    ModuleManager getModuleManager();

    Server getServer();

    StageManager getStageManager();

    AssetManager getAssetManager();
}
