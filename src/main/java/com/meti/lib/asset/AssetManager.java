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
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetManager extends ServerComponent<AssetEvent, AssetUpdate> {
    private final Set<AssetBuilder<?>> builders = new HashSet<>();
    public final Set<Asset> assets = new HashSet<>();

    {
        builders.add(new DefaultAssetBuilder());
    }

    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return null;
    }

    public void load(Path path) throws IOException {
        assets.addAll(loadAsStream(null, path).collect(Collectors.toSet()));
    }

    public Stream<Asset> loadAsStream(Asset parent, Path path) throws IOException {
        Stream<Asset> toReturn;
        if (Files.isDirectory(path)) {
            DirectoryAsset directoryAsset = new DirectoryAsset(path);
            Files.list(path)
                    .flatMap((Function<Path, Stream<Asset>>) child -> {
                        try {
                            return loadAsStream(directoryAsset, child);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).forEach(directoryAsset::add);
            toReturn = Stream.of(directoryAsset);
        } else {
            toReturn = builders.stream()
                    .filter(assetBuilder -> assetBuilder.test(path))
                    .map(assetBuilder -> assetBuilder.apply(path))
                    .map(new TypeFunction<>(Asset.class));
        }

        return toReturn.peek(asset -> eventManager.fire(AssetEvent.ON_INDEX, new AssetUpdate(asset)));
    }

    private static class DefaultAssetBuilder implements AssetBuilder<Asset> {
        @Override
        public Asset apply(Path path) {
            return new Asset() {
                @Override
                public String getName() {
                    return path.getFileName().toString();
                }

                @Override
                public long size() throws IOException {
                    return Files.size(path);
                }
            };
        }

        @Override
        public boolean test(Path path) {
            return false;
        }
    }
}
