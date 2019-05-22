package com.meti.lib.source;

import com.meti.lib.source.socket.SocketSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class SocketSupplier implements SourceSupplier<InputStream, OutputStream, SocketSource> {
    private final ServerSocket serverSocket;

    public SocketSupplier(int port) throws IOException {
        this(new ServerSocket(port));
    }

    private SocketSupplier(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public SocketSource next() {
        return null;
    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
