package com.meti.lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Function;

public abstract class Server<C extends Client> implements Callable<Optional<Exception>> {
    private final Function<Socket, C> clientBuilder;
    private final ServerSocket serverSocket;

    public Server(Function<Socket, C> clientBuilder, ServerSocket serverSocket) {
        this.clientBuilder = clientBuilder;
        this.serverSocket = serverSocket;
    }

    @Override
    public Optional<Exception> call() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                handleClient(clientBuilder.apply(socket));
            }

            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    public abstract void handleClient(C client);
}