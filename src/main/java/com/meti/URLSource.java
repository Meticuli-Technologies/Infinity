package com.meti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

public class URLSource implements Source {
    private final URL url;

    public URLSource(URL url) {
        Objects.requireNonNull(url);
        this.url = url;
    }

    public static URLSource of(String resourcePath) {
        return of(URLSource.class, resourcePath);
    }

    public static URLSource of(Class<URLSource> clazz, String resourcePath) {
        URL url = clazz.getResource(resourcePath);
        if(url == null){
            throw new IllegalArgumentException("Could not find resource at path: " + resourcePath);
        }
        return new URLSource(url);
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
