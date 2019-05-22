package com.meti.lib.source;

import com.meti.lib.source.socket.SocketSource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;

public class SocketSupplier implements SourceSupplier<CompoundSource<?, ?>>, PortUser {
    private final ServerSocket serverSocket;

    public SocketSupplier(int port) throws IOException {
        this(new ServerSocket(port));
    }

    @Override
    public int getLocalPort() {
        return serverSocket.getLocalPort();
    }

    private SocketSupplier(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public boolean isClosed() {
        return serverSocket.isClosed();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    @Override
    public CompoundSource<?, ?> next() throws IOException {
        return new SocketSource(serverSocket.accept());
    }
}
