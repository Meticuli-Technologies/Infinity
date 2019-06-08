package com.meti.app.server.asset;

import com.meti.lib.asset.properties.AssetProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class ListBasedAssetPropertiesRequestResponse implements AssetPropertiesRequestResponse {
    private static final long serialVersionUID = 3371764965492375753L;

    @SuppressWarnings("NonSerializableFieldInSerializableClass")
    private final List<AssetProperties> properties = new ArrayList<>();

    public ListBasedAssetPropertiesRequestResponse(Collection<? extends AssetProperties> properties) {
        this.properties.addAll(properties);
    }

    @Override
    public List<AssetProperties> getProperties() {
        return properties;
    }
}
