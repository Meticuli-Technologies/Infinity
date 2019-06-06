package com.meti.lib.asset;

import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface AssetManager {
    void addTranslator(AssetTranslator<?> builder);

    void read(Source source) throws IOException;

    Set<Asset> getAssets();
}
