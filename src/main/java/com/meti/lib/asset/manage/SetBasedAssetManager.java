package com.meti.lib.asset.manage;

import com.meti.lib.asset.Asset;
import com.meti.lib.asset.AssetTranslator;
import com.meti.lib.asset.source.ParentSource;
import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SetBasedAssetManager implements AssetManager {
    private final Collection<AssetTranslator<?>> translators = new HashSet<>();
    private final Map<Source, Set<Asset<?, ?>>> assetMap = new HashMap<>();
    private Source rootSource;

    @Override
    public Asset<?, ?> getAssetByName(String name) {
        return assetMap.values().stream()
                .flatMap(Collection::stream)
                //TODO: fix LoD violation
                .filter(asset -> asset.getProperties().getName().equals(name))
                .findAny()
                .orElseThrow();
    }

    private void buildChild(ParentSource source, Source child) throws IOException {
        Optional<Source> rootSourceOptional = getRootSource();
        if (rootSourceOptional.isPresent()) {
            if (!(source.equals(rootSourceOptional.get()))) {
                buildChildWithParent(source, child);
            }
            else{
                read(child);
            }
        } else {
            buildChildWithParent(source, child);
        }
    }

    @Override
    public Optional<Source> getRootSource() {
        return Optional.ofNullable(rootSource);
    }

    @Override
    public Set<Asset<?, ?>> read(Source source) throws IOException {
        Set<Asset<?, ?>> builtSet = new HashSet<>();
        for (AssetTranslator<?> builder : translators) {
            if (builder.canUse(source)) {
                Asset<?, ?> asset = builder.read(source);
                builtSet.add(asset);
            }
        }

        assetMap.put(source, builtSet);
        if(source instanceof ParentSource){
            buildParent((ParentSource) source);
        }

        return builtSet;
    }

    @Override
    public Set<Asset<?, ?>> getAssets() {
        return assetMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public void write() throws IOException {
        for (Map.Entry<Source, Set<Asset<?, ?>>> sourceSetEntry : assetMap.entrySet()) {
            Set<Asset<?, ?>> assets = sourceSetEntry.getValue();
            Optional<Asset<?, ?>> optional = assets.stream().findAny();
            if (optional.isPresent()) {
                Asset<?, ?> asset = optional.get();
                Set<AssetTranslator<?>> validTranslators = translators.stream()
                        .filter(assetTranslator -> assetTranslator.canUse(sourceSetEntry.getKey()))
                        .collect(Collectors.toSet());
                for (AssetTranslator<?> validTranslator : validTranslators) {
                    validTranslator.write(sourceSetEntry.getKey(), asset);
                }
            }
        }
    }

    @Override
    public void addTranslator(AssetTranslator<?> translator) {
        translators.add(translator);
    }

    @Override
    public void setRootSource(Source rootSource) {
        this.rootSource = rootSource;
    }

    private void buildChildWithParent(ParentSource parentSource, Source childSource) throws IOException {
        read(childSource).stream()
                .map(Asset::getProperties)
                .forEach(assetProperties -> assetProperties.setParentName(parentSource.getName()));
    }

    private void buildParent(ParentSource source) throws IOException {
        for (Source child : source.getChildren()) {
            buildChild(source, child);
        }
    }
}
