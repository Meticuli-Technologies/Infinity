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
    private final Source<I, O> source;
    private final I inputStream;
    private final O outputStream;

    public Client(Source<I, O> source) throws IOException {
        this.source = source;
        this.inputStream = source.getInputStream();
        this.outputStream = source.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();

        source.close();
    }
}
