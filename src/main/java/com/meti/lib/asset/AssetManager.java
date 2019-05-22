package com.meti.lib.asset;

import com.meti.lib.source.NamedParentSource;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class AssetManager implements AssetManagerImpl {
    private final Set<AssetTransformer> transformers = new HashSet<>();
    private final Set<Asset> assets = new HashSet<>();

    @Override
    public Set<Asset> getAssets() {
        return Collections.unmodifiableSet(assets);
    }

    @Override
    public <T extends NamedParentSource<T>> void load(T source) throws IOException {
        for (T subSource : source.getSubSources()) {
            load(subSource);
        }

        transformers.stream()
                .filter(assetTransformer -> assetTransformer.canRead(source))
                .map(assetTransformer -> assetTransformer.read(source))
                .forEach(assets::add);
    }
}
