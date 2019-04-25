package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server implements Closeable {
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    public boolean isOpen() {
        return isClosed();
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }
}
