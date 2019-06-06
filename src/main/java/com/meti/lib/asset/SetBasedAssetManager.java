package com.meti.lib.asset;

import com.meti.lib.asset.source.Source;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SetBasedAssetManager implements AssetManager {
    private final Collection<AssetBuilder<?>> builders = new HashSet<>();
    private final Set<Asset> assets = new HashSet<>();

    @Override
    public void addBuilder(AssetBuilder<?> builder) {
        builders.add(builder);
    }

    @Override
    public void build(Source source) {
        for (AssetBuilder<?> builder : builders) {
            if (builder.canBuild(source)) {
                Asset asset = builder.build(source);
                assets.add(asset);
            }
        }
    }

    @Override
    public Set<Asset> getAssets() {
        return assets;
    }
}
