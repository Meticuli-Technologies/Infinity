package com.meti.lib.io;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketSupplier implements SourceSupplier<SocketSource> {
    private final ServerSocket serverSocket;

    public ServerSocketSupplier(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public SocketSource get() {
        try {
            return new SocketSource(serverSocket.accept());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    @Override
    public boolean isOpen() {
        return !isClosed();
    }
}