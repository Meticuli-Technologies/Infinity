package com.meti.lib.asset;

import com.meti.lib.event.Component;
import com.meti.lib.event.ServerComponent;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

public class AssetManager extends ServerComponent<AssetEvent, AssetUpdate> {
    private final Set<AssetBuilder<?>> builders = new HashSet<>();
    private final Map<String, Asset> map = new HashMap<>();

    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return null;
    }

    public void load(Path path) throws IOException {
        Files.walk(path).forEach(path1 -> builders.stream()
                .filter(assetBuilder -> assetBuilder.test(path1))
                .map((Function<AssetBuilder<?>, Asset>) assetBuilder -> assetBuilder.apply(path1))
                .forEach(asset -> map.put(asset.getName(), asset)));
    }
}
