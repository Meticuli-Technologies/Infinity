package com.meti.asset;

import com.meti.net.source.Source;
import com.meti.net.source.SuperSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class AssetManager {
    private final Map<String, Asset> assetMap = new HashMap<>();
    private final Set<AssetBuilder<?>> builders = new HashSet<>();

    private void load(Source source) throws IOException {
        if (source instanceof SuperSource) {
            loadSuperSource((SuperSource) source);
        }
        loadFromBuilders(source);
    }

    private void loadFromBuilders(Source source) throws IOException {
        for (AssetBuilder<?> builder : builders) {
            if (builder.canBuild(source)) {
                useBuilder(source, builder);
            }
        }
    }

    private void useBuilder(Source source, AssetBuilder<?> builder) throws IOException {
        Asset asset = builder.build(source);
        assetMap.put(asset.getName(), asset);
    }

    private void loadSuperSource(SuperSource source) throws IOException {
        for (Source subSource : source.getSubSources()) {
            load(subSource);
        }
    }
}
