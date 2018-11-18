package com.meti.lib.net.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2018
 */
abstract class Connection {
    private final InputStream inputStream;
    private final OutputStream outputStream;

    Connection(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    void close() throws IOException {
        inputStream.close();
        outputStream.close();

        closeSource();
    }

    protected abstract void closeSource() throws IOException;
}
