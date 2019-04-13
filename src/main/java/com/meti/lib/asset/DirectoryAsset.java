package com.meti.lib.asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DirectoryAsset extends Asset {
    public final ArrayList<Asset> assets = new ArrayList<>();
    private final Path path;

    public DirectoryAsset(Path path) {
        this.path = path;
    }

    public void add(Asset asset) {
        assets.add(asset);
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public long size() throws IOException {
        return Files.size(path);
    }
}
