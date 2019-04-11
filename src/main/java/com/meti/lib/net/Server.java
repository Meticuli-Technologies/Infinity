package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Server implements Callable<Void>, Closeable {
    public final List<Client> clients = new ArrayList<>();
    protected final ServerSocket serverSocket;
    protected final ExecutorService service;
    public Consumer<Client> onAccept;
    public Function<Client, TokenHandler> handlerFactory;

    public Server(ServerSocket serverSocket, ExecutorService service) {
        this.serverSocket = serverSocket;
        this.service = service;
    }

    @Override
    public Void call() throws Exception {
        while (!serverSocket.isClosed()) {
            Client client = new Client(serverSocket.accept());
            clients.add(client);

            if (onAccept != null) {
                onAccept.accept(client);
            }

            if (handlerFactory != null) {
                TokenHandler handler = handlerFactory.apply(client);
                service.submit(handler);
            } else {
                throw new IllegalStateException("Handler factory has not been set!");
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }
}
