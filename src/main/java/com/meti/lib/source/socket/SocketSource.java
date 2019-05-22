package com.meti.lib.source.socket;

import com.meti.lib.source.CompoundSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketSource implements CompoundSource<InputStream, OutputStream> {
    private final Socket socket;

    public SocketSource(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    public SocketSource(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public boolean isClosed() {
        return socket.isClosed();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
