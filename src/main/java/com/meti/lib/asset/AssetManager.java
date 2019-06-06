package com.meti.lib.asset;

import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface AssetManager {
    void addBuilder(AssetBuilder<?> builder);

    void build(Asset asset);

    Set<Asset> getAssets();
}
