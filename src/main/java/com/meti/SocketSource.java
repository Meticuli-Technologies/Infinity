package com.meti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class SocketSource implements Source<InputStream, OutputStream> {
    private final Socket socket;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public SocketSource(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
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
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();

        socket.close();
    }
}
