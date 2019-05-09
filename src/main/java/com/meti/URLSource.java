package com.meti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class URLSource implements Source {
    private final URL url;

    public URLSource(URL url) {
        this.url = url;
    }

    public static URLSource of(String resource) {
        return of(URLSource.class, resource);
    }

    public static URLSource of(Class<URLSource> clazz, String resource) {
        return new URLSource(clazz.getResource(resource));
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("URLs cannot be closed.");
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return url.openStream();
    }

    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException("URLs cannot be written to.");
    }

    @Override
    public boolean isClosed() {
        throw new UnsupportedOperationException("URLs cannot be closed.");
    }
}
