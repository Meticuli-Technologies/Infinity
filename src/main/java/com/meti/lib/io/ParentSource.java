package com.meti.lib.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ParentSource<S extends Source> implements Source {
    private final S source;

    public ParentSource(S source) {
        this.source = source;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return source.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return source.getOutputStream();
    }

    @Override
    public boolean isClosed() {
        return source.isClosed();
    }

    @Override
    public boolean isOpen() {
        return source.isOpen();
    }
}
