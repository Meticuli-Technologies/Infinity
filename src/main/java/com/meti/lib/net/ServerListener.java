package com.meti.lib.net;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
class ServerListener implements Callable<Set<Client>> {
    public final BooleanProperty runningProperty = new SimpleBooleanProperty();
    private final Set<Client> clients = new HashSet<>();
    private final ClientConsumer clientConsumer;
    private final ServerSocket serverSocket;

    ServerListener(ClientConsumer clientConsumer, ServerSocket serverSocket) {
        this.clientConsumer = clientConsumer;
        this.serverSocket = serverSocket;
    }

    @Override
    public Set<Client> call() throws Exception {
        while (runningProperty.get()) {
            Socket socket = serverSocket.accept();
            Client client = new Client(socket.getInputStream(), socket.getOutputStream());
            clients.add(client);
            clientConsumer.accept(client);
        }
        return clients;
    }
}
