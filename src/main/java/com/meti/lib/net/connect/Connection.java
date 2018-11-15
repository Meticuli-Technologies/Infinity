package com.meti.lib.net.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2018
 */
public abstract class Connection {
    public final InputStream inputStream;
    public final OutputStream outputStream;

    public Connection(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();

        closeSource();
    }

    public abstract void closeSource() throws IOException;
}
