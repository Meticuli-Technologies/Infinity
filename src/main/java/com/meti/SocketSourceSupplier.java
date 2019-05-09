package com.meti;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketSourceSupplier implements SourceSupplier {
    private final ServerSocket serverSocket;

    public SocketSourceSupplier() throws IOException {
        this(0);
    }

    public SocketSourceSupplier(int port) throws IOException {
        this(new ServerSocket(port));
    }

    public SocketSourceSupplier(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Source accept() throws IOException {
        return new SocketSource(serverSocket.accept());
    }

    @Override
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    public int getLocalPort() {
        return serverSocket.getLocalPort();
    }
}
