package com.meti.lib.source;

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
 * @since 5/22/2019
 */
public class PathSource implements PathSourceImpl {
    private final Path path;
    private final InputStream inputStream;
    private final OutputStream outputStream;
    private boolean closed = false;

    public PathSource(Path path) throws IOException {
        this.path = path;
        this.inputStream = Files.newInputStream(path);
        this.outputStream = Files.newOutputStream(path);
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public Set<? extends PathSourceImpl> getSubSources() throws IOException {
        if (Files.isDirectory(path)) {
            Set<Path> children = Files.list(path).collect(Collectors.toSet());
            Set<PathSourceImpl> sources = new HashSet<>();
            for (Path child : children) {
                sources.add(new PathSource(child));
            }
            return sources;
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() throws IOException {
        closed = true;
        inputStream.close();
        outputStream.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return outputStream;
    }
}
