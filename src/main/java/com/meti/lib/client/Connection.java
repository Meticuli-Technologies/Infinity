package com.meti.lib.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public class Connection {
    public final InputStream inputStream;
    public final OutputStream outputStream;

    private boolean closed;

    public Connection(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public boolean close() throws IOException {
        if (!closed) {
            inputStream.close();
            outputStream.close();

            closed = true;
        }

        return closed;
    }
}
