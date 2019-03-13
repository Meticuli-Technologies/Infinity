package com.meti.lib;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;

public class Server implements Callable<Optional<Exception>> {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Optional<Exception> call() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                Client client = new Client(socket);
            }

            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }
}