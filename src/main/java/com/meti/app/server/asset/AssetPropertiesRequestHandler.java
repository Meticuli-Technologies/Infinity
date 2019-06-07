package com.meti.app.server.asset;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.manage.AssetManager;
import com.meti.lib.asset.properties.AssetProperties;
import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class AssetPropertiesRequestHandler extends TypeHandler<AssetPropertiesRequest> {
    private final AssetManager assetManager;

    public AssetPropertiesRequestHandler(AssetManager assetManager) {
        super(AssetPropertiesRequest.class);
        this.assetManager = assetManager;
    }

    @Override
    public Optional<Serializable> handleGeneric(AssetPropertiesRequest response, Client client) {
        List<AssetProperties> assetPropertiesList = getAssetPropertiesList(assetManager);
        return Optional.of(new ArrayList<>(assetPropertiesList));
    }

    private List<AssetProperties> getAssetPropertiesList(AssetManager assetManager) {
        return assetManager.getAssets()
                .stream()
                .map(Asset::getProperties)
                .collect(Collectors.toList());
    }
}
