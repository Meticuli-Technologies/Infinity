package com.meti.lib.asset.manage;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetTranslator;
import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface AssetManager {
    Asset<?, ?> getAssetByName(String name);

    Optional<Source> getRootSource();

    void addTranslator(AssetTranslator<?> builder);

    Set<Asset<?, ?>> read(Source source) throws IOException;

    Set<Asset<?, ?>> getAssets();

    void setRootSource(Source rootSource);

    void write() throws IOException;
}
