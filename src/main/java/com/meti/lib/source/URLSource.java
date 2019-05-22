package com.meti.lib.source;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class URLSource implements Source, Readable<InputStream> {
    private final InputStream inputStream;
    private boolean closed;

    public static URLSource fromResource(String resourcePath) throws IOException {
        URL resource = URLSource.class.getResource(resourcePath);
        if (resource == null) throw new IllegalArgumentException("Cannot find resource at \"" + resourcePath);
        return new URLSource(resource);
    }

    public URLSource(URL url) throws IOException {
        this.inputStream = url.openStream();
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        closed = true;
    }
}
