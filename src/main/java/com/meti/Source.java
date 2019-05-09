package com.meti;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public interface Source extends Closeable {
    InputStream getInputStream();

    OutputStream getOutputStream();

    default boolean isOpen() {
        return !isClosed();
    }

    boolean isClosed();
}
