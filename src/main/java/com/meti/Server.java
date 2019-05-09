package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
            manager.submit(new SocketAcceptor(handler, accepted));
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

    private class SocketAcceptor implements Callable<SocketAcceptor> {
        private final TokenHandler handler;
        private final Socket socket;
        private final ObjectInputStream inputStream;
        private final ObjectOutputStream outputStream;

        public SocketAcceptor(TokenHandler handler, Socket socket) throws IOException {
            this.handler = handler;
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public SocketAcceptor call() throws Exception {
            while (!socket.isClosed()) {
                Object token = inputStream.readUnshared();
                Object toWrite = handle(token);
                outputStream.writeUnshared(toWrite);
                outputStream.flush();
            }
            return this;
        }

        public Object handle(Object token) {
            if (handler.canHandle(token)) {
                return handler.handle(token);
            } else {
                return new IllegalArgumentException("Cannot handle " + token.toString());
            }
        }
    }
}