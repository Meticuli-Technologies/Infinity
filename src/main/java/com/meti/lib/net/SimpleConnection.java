package com.meti.lib.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SimpleConnection implements Connection<Integer, InputStream, OutputStream> {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SimpleConnection(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
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
    public Integer read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void write(Integer integer) throws IOException {
        outputStream.write(integer);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
