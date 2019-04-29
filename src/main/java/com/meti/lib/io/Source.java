package com.meti.lib.io;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public interface Source extends Closeable {
    InputStream getInputStream();

    OutputStream getOutputStream();

    boolean isClosed();

    boolean isOpen();
}
