package com.meti.app.client;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.properties.AssetProperties;
import javafx.scene.Parent;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface Editor {
    boolean canRender(AssetProperties properties);

    Parent getRoot();

    String getName();

    void render(Asset<?, ?> asset);
}
