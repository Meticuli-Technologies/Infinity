package com.meti.app.server;

import com.meti.lib.client.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.Callable;

public class ServerListener implements Callable<Optional<Exception>> {
    private InfinityServer infinityServer;

    public ServerListener(InfinityServer infinityServer) {
        this.infinityServer = infinityServer;
    }

    @Override
    public Optional<Exception> call() {
        infinityServer.logger.info("Listening for clients on " + infinityServer.serverSocket.getInetAddress().toString());

        while (!Thread.interrupted()) {
            try {
                Socket socket = infinityServer.serverSocket.accept();
                Client client = new Client(socket);

                infinityServer.logger.info("Located client at " + socket.getInetAddress().toString());

                infinityServer.clientManager.put(socket.getInetAddress(), client);
            } catch (IOException e) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }
}
