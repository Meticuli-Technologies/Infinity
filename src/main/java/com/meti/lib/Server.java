package com.meti.lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Server<C extends Client> implements Callable<Optional<Exception>> {
    private final Consumer<Callable<Optional<Exception>>> consumer;
    private final Function<Socket, C> clientBuilder;
    private final ServerSocket serverSocket;

    public Server(ServerSocket serverSocket, Consumer<Callable<Optional<Exception>>> consumer, Function<Socket, C> clientBuilder) {
        this.consumer = consumer;
        this.clientBuilder = clientBuilder;
        this.serverSocket = serverSocket;
    }

    public void listen() {
        consumer.accept(this);
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