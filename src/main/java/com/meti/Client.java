package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class Client<I extends InputStream, O extends OutputStream> implements Closeable {
    private final I inputStream;
    private final O outputStream;

    public Client(I inputStream, O outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
