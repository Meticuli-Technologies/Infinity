package com.meti.app.server;

import com.meti.lib.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;

public class ServerListener implements Callable<Optional<Exception>> {
    private Server server;

    public ServerListener(Server server) {
        this.server = server;
    }

    @Override
    public Optional<Exception> call() {
        server.logger.info("Listening for clients on " + server.serverSocket.getInetAddress().toString());

        while (!Thread.interrupted()) {
            try {
                Socket socket = server.serverSocket.accept();
                Client client = new Client(socket);

                server.logger.info("Located client at " + socket.getInetAddress().toString());

                server.clientManager.put(socket.getInetAddress(), client);
            } catch (IOException e) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }
}
