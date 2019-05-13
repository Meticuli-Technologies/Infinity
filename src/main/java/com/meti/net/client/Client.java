package com.meti.net.client;

import com.meti.net.source.Source;

import java.io.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Client implements Closeable, Source {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private Source source;

    public Client(Source source) throws IOException {
        this.outputStream = new ObjectOutputStream(source.getOutputStream());
        this.inputStream = new ObjectInputStream(source.getInputStream());
        this.source = source;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public InputStream getInputStream() {
        return inputStream;
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public boolean isClosed() {
        return source.isClosed();
    }

    public Object read() throws IOException, ClassNotFoundException {
        return inputStream.readUnshared();
    }

    public void write(Object token) throws IOException {
        outputStream.writeUnshared(token);
    }
}