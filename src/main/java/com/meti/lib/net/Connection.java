package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Connection<T, I extends InputStream, O extends OutputStream> extends Closeable {
    I getInputStream();

    O getOutputStream();

    T read() throws Exception;

    void write(T t) throws IOException;

    void flush() throws IOException;
}
