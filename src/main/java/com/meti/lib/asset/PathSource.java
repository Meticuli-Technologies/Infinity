package com.meti.lib.asset;

import com.meti.lib.asset.source.ParentSource;
import com.meti.lib.asset.source.Source;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class PathSource implements ParentSource {
    private final Path path;

    public PathSource(Path path) {
        this.path = path;
    }

    @Override
    public Set<Source> getChildren() throws IOException {
        Set<Source> children = new HashSet<>();
        if (Files.isDirectory(path)) {
            Set<Path> subPaths = Files.list(path).collect(Collectors.toSet());
            for (Path subPath : subPaths) {
                children.add(new PathSource(subPath));
            }
        }
        return children;
    }

    @Override
    public InputStream newInputStream() throws IOException {
        return Files.newInputStream(path);
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public OutputStream newOutputStream() throws IOException {
        return Files.newOutputStream(path);
    }
}
