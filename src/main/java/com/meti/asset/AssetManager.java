package com.meti.asset;

import com.meti.net.source.Source;
import com.meti.net.source.SuperSource;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AssetManager {
    private final Map<String, Asset> assetMap = new HashMap<>();
    private final Set<AssetBuilder<?>> builders = new HashSet<>();

    public void load(Source source) throws IOException {
        if (source instanceof SuperSource) {
            SuperSource superSource = (SuperSource) source;
            for (Source subSource : superSource.getSubSources()) {
                load(subSource);
            }
        }

        for (AssetBuilder<?> builder : builders) {
            if (builder.canBuild(source)) {
                Asset asset = builder.build(source);
                assetMap.put(asset.getName(), asset);
            }
        }
    }
}
