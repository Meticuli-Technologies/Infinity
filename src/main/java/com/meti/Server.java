package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Server implements Callable<Server>, Closeable {
    private final Set<TokenHandler> handlers = new HashSet<>();
    private final ExecutorServiceManager manager;
    private final ServerSocket serverSocket;

    public Server(ExecutorServiceManager manager, int port) throws IOException {
        this.manager = manager;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public Server call() throws Exception {
        while (!serverSocket.isClosed()) {
            Socket accepted = serverSocket.accept();
            manager.submit(new SocketAcceptor(accepted));
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
        private final Socket socket;
        private final ObjectInputStream inputStream;
        private final ObjectOutputStream outputStream;

        public SocketAcceptor(Socket socket) throws IOException {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }

        @Override
        public SocketAcceptor call() throws Exception {
            while (!socket.isClosed()) {
                Object token = inputStream.readUnshared();
                Object toWrite = handlers.parallelStream()
                        .filter(tokenHandler -> tokenHandler.canHandle(token))
                        .map(tokenHandler -> tokenHandler.handle(token))
                        .findAny()
                        .orElseGet(() -> new IllegalArgumentException("No results found"));
                outputStream.writeUnshared(toWrite);
                outputStream.flush();
            }
            return this;
        }
    }

}