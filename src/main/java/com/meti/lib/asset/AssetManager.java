package com.meti.lib.asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class AssetManager extends HashMap<String, Asset> {
    private final Set<AssetBuilder<?>> builders = new HashSet<>();

    public void load(Path path) throws IOException {
        Files.walk(path).forEach(path1 -> builders.stream()
                .filter(assetBuilder -> assetBuilder.test(path1))
                .map((Function<AssetBuilder<?>, Asset>) assetBuilder -> assetBuilder.apply(path1))
                .forEach(asset -> put(asset.getName(), asset)));
    }
}
