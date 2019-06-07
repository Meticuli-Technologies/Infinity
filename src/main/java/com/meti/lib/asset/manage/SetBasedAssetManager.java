package com.meti.lib.asset.manage;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetTranslator;
import com.meti.lib.asset.source.ParentSource;
import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SetBasedAssetManager implements AssetManager {
    private final Collection<AssetTranslator<?>> builders = new HashSet<>();
    private final Set<Asset<?, ?>> assets = new HashSet<>();

    @Override
    public void addTranslator(AssetTranslator<?> builder) {
        builders.add(builder);
    }

    @Override
    public Set<Asset<?, ?>> read(Source source) throws IOException {
        Set<Asset<?, ?>> builtSet = new HashSet<>();
        for (AssetTranslator<?> builder : builders) {
            if (builder.canBuild(source)) {
                Asset<?, ?> asset = builder.read(source);
                builtSet.add(asset);
            }
        }
        assets.addAll(builtSet);

        if(source instanceof ParentSource){
            buildParent((ParentSource) source);
        }

        return builtSet;
    }

    @Override
    public Set<Asset<?, ?>> getAssets() {
        return assets;
    }

    private void buildChild(ParentSource source, Source child) throws IOException {
        read(child).stream()
                .map(Asset::getProperties)
                .forEach(assetProperties -> assetProperties.setParentName(source.getName()));
    }

    private void buildParent(ParentSource source) throws IOException {
        for (Source child : source.getChildren()) {
            buildChild(source, child);
        }
    }
}
