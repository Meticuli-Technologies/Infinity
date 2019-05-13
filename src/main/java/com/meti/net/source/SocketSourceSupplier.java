package com.meti.net.source;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Consumer;

public class SocketSourceSupplier implements SourceSupplier {
    private final ServerSocket serverSocket;
    private Consumer<SocketSource> onAccept;

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
        SocketSource source = new SocketSource(serverSocket.accept());
        if (onAccept != null) {
            onAccept.accept(source);
        }
        return source;
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

    public void setOnAccept(Consumer<SocketSource> onAccept) {
        this.onAccept = onAccept;
    }
}
