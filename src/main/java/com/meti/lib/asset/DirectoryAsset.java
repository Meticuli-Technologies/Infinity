package com.meti.lib.asset;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class DirectoryAsset implements Asset {
    private final ArrayList<Asset> assets = new ArrayList<>();
    private final Asset parent;
    private final Path path;

    public DirectoryAsset(Asset parent, Path path) {
        this.parent = parent;
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
    public Asset getParent() {
        return parent;
    }

    @Override
    public long size() throws IOException {
        return Files.size(path);
    }
}
