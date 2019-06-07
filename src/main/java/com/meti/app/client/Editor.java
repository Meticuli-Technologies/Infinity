package com.meti.app.client;

import com.meti.lib.asset.properties.AssetProperties;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface Editor {
    boolean canRender(AssetProperties properties);

    String getName();
}
