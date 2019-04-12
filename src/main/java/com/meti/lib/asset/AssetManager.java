package com.meti.lib.asset;

import com.meti.lib.event.ServerComponent;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;
import com.meti.lib.util.TypeFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetManager extends ServerComponent<AssetEvent, AssetUpdate> {
    private final Set<AssetBuilder<?>> builders = new HashSet<>();
    private final Set<Asset> assets = new HashSet<>();

    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return null;
    }

    public void load(Path path) throws IOException {
        assets.addAll(loadAsStream(null, path).collect(Collectors.toSet()));
    }

    public Stream<Asset> loadAsStream(Asset parent, Path path) throws IOException {
        if (Files.isDirectory(path)) {
            DirectoryAsset directoryAsset = new DirectoryAsset(parent, path);
            Files.list(path)
                    .flatMap((Function<Path, Stream<Asset>>) child -> {
                        try {
                            return loadAsStream(directoryAsset, child);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).forEach(directoryAsset::add);
            return Stream.of(directoryAsset);
        }

        return builders.stream()
                .filter(assetBuilder -> assetBuilder.test(path))
                .map(assetBuilder -> assetBuilder.apply(path))
                .map(new TypeFunction<>(Asset.class))
                .peek(asset -> eventManager.fire(AssetEvent.ON_INDEX, new AssetUpdate(asset)));
    }
}
