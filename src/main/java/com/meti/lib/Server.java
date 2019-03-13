package com.meti.lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;

public abstract class Server<C extends Client, F extends Function<Callable<Optional<Exception>>, Future<Optional<Exception>>>> implements Callable<Optional<Exception>> {
    private final F function;
    private final Function<Socket, C> clientBuilder;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, F function, Function<Socket, C> clientBuilder) {
        this.function = function;
        this.clientBuilder = clientBuilder;
        this.serverSocket = serverSocket;
    }

    public Future<Optional<Exception>> listen() {
        return function.apply(this);
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