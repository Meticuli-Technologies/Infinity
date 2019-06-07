package com.meti.app.server.asset;

import com.meti.lib.asset.properties.AssetProperties;

import java.io.Serializable;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface AssetPropertiesRequestResponse extends Serializable {
    List<AssetProperties> getProperties();
}
