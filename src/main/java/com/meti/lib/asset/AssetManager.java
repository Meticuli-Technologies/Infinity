package com.meti.lib.asset;

import com.meti.lib.event.ServerComponent;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;
import com.meti.lib.util.TypeFunction;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetManager extends ServerComponent<AssetEvent, AssetUpdate> {
    public final Set<AssetBuilder<?>> builders = new HashSet<>();
    public final Set<Asset> assets = new HashSet<>();

    {
        builders.add(new DefaultAssetBuilder());
    }

    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return null;
    }

    public void load(Path path) throws IOException {
        assets.addAll(loadAsStream(path).collect(Collectors.toSet()));
    }

    public Stream<Asset> loadAsStream(Path path) throws IOException {
        Stream<Asset> toReturn;
        if (Files.isDirectory(path)) {
            DirectoryAsset directoryAsset = new DirectoryAsset(path);
            Files.list(path)
                    .flatMap((Function<Path, Stream<Asset>>) child -> {
                        try {
                            return loadAsStream(child);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).forEach(directoryAsset::add);
            toReturn = Stream.of(directoryAsset);
        } else {
            InputStream inputStream = Files.newInputStream(path);
            String name = path.getFileName().toString();

            toReturn = builders.stream()
                    .filter(assetBuilder -> assetBuilder.test(name))
                    .map(assetBuilder -> assetBuilder.apply(inputStream, name))
                    .map(new TypeFunction<>(Asset.class));
        }

        return toReturn.peek(asset -> eventManager.fire(AssetEvent.ON_INDEX, new AssetUpdate(asset)));
    }

    private static class DefaultAssetBuilder implements AssetBuilder<Asset> {
        @Override
        public Asset apply(InputStream inputStream, String s) {
            return new InputStreamAsset(inputStream, s);
        }

        @Override
        public boolean test(String s) {
            return true;
        }
    }
}
