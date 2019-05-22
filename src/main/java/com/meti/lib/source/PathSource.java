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
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean closed = false;

    public PathSource(Path path) {
        this.path = path;
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public PathSourceImpl ensure(boolean directory) throws IOException {
        if (!Files.exists(path)) {
            if (directory) Files.createDirectory(path);
            else Files.createFile(path);
        }
        return new PathSource(path);
    }

    @Override
    public Set<? extends PathSourceImpl> getSubSources() throws IOException {
        Set<PathSourceImpl> sources = new HashSet<>();
        if (Files.isDirectory(path)) {
            Set<Path> children = Files.list(path).collect(Collectors.toSet());
            for (Path child : children) {
                sources.add(new PathSource(child));
            }
        }
        return sources;
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
        if (inputStream == null) {
            throwIfDirectory();
            inputStream = Files.newInputStream(path);
        }
        return inputStream;
    }

    private void throwIfDirectory() {
        if (Files.isDirectory(path)) {
            throw new UnsupportedOperationException(path + " is a directory");
        }
    }

    @Override
    public String getName() {
        return path.getFileName().toString();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        if (outputStream == null) {
            throwIfDirectory();
            outputStream = Files.newOutputStream(path);
        }
        return outputStream;
    }
}
