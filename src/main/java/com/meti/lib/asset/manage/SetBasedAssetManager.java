package com.meti.lib.asset.manage;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetTranslator;
import com.meti.lib.asset.source.ParentSource;
import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SetBasedAssetManager implements AssetManager {
    private final Collection<AssetTranslator<?>> builders = new HashSet<>();
    private final Set<Asset<?, ?>> assets = new HashSet<>();
    private Source rootSource;

    private void buildChild(ParentSource source, Source child) throws IOException {
        Optional<Source> rootSourceOptional = getRootSource();
        if (rootSourceOptional.isPresent()) {
            if (!(source.equals(rootSourceOptional.get()))) {
                buildChildWithParent(source, child);
            }
            else{
                buildChildWithoutParent(child);
            }
        } else {
            buildChildWithParent(source, child);
        }
    }

    private void buildChildWithoutParent(Source childSource) throws IOException {
        assets.addAll(read(childSource));
    }

    @Override
    public Optional<Source> getRootSource() {
        return Optional.ofNullable(rootSource);
    }

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

    @Override
    public void setRootSource(Source rootSource) {
        this.rootSource = rootSource;
    }

    private void buildChildWithParent(ParentSource parentSource, Source childSource) throws IOException {
        read(childSource).stream()
                .peek(assets::add)
                .map(Asset::getProperties)
                .forEach(assetProperties -> assetProperties.setParentName(parentSource.getName()));
    }

    private void buildParent(ParentSource source) throws IOException {
        for (Source child : source.getChildren()) {
            buildChild(source, child);
        }
    }
}
