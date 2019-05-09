package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Source extends Closeable {
    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    default boolean isOpen() {
        return !isClosed();
    }

    boolean isClosed();
}
