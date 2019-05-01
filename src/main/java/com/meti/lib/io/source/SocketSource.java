package com.meti.lib.io.source;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketSource implements Source {
    public final Socket socket;

    public SocketSource(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    private boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public boolean isOpen() {
        return !isClosed();
    }
}
