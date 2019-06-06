package com.meti.lib.asset;

import com.meti.lib.asset.source.ParentSource;
import com.meti.lib.asset.source.Source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public PathSource(Path path) throws IOException {
        this.path = path;
        this.inputStream = Files.newInputStream(path);
        this.outputStream = Files.newOutputStream(path);
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
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
