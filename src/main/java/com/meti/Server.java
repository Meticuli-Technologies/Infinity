package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Server implements Callable<Server>, Closeable {
    private final ExecutorServiceManager manager;
    private final ServerSocket serverSocket;
    private final TokenHandler handler;

    public Server(int port, ExecutorServiceManager manager, TokenHandler handler) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.manager = manager;
        this.handler = handler;
    }

    @Override
    public Server call() throws Exception {
        while (!serverSocket.isClosed()) {
            Socket accepted = serverSocket.accept();
            manager.submit(new ServerHandler(handler, accepted));
        }

        return this;
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    public Server listen() {
        manager.submit(this);
        return this;
    }

}