package com.meti.lib.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2018
 */
public abstract class Connection {
    private InputStream inputStream;
    private OutputStream outputStream;

    public InputStream getInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = constructInputStream();
        }
        return inputStream;
    }

    public abstract InputStream constructInputStream() throws IOException;

    public OutputStream getOutputStream() throws IOException {
        if (outputStream == null) {
            outputStream = constructOutputStream();
        }
        return outputStream;
    }

    public abstract OutputStream constructOutputStream() throws IOException;
}
