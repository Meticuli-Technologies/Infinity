package com.meti.app.server.asset;

import com.meti.lib.asset.properties.AssetProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class AssetPropertiesRequestResponse implements Serializable {
    @SuppressWarnings("NonSerializableFieldInSerializableClass")
    private final List<AssetProperties> properties = new ArrayList<>();

    public AssetPropertiesRequestResponse(Collection<? extends AssetProperties> properties) {
        this.properties.addAll(properties);
    }

    public List<AssetProperties> getProperties() {
        return properties;
    }
}
