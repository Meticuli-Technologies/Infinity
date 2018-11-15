package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.ClientConsumer;
import com.meti.lib.net.connect.SocketConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class ServerListener implements Callable<Optional<Set<Client<SocketConnection>>>> {
    public final ObservableSet<Client<SocketConnection>> clients = FXCollections.observableSet(new HashSet<>());
    public final BooleanProperty runningProperty = new SimpleBooleanProperty();
    private final ClientConsumer clientConsumer;
    private final ServerSocket serverSocket;

    ServerListener(ClientConsumer clientConsumer, ServerSocket serverSocket) {
        this.clientConsumer = clientConsumer;
        this.serverSocket = serverSocket;
    }

    @Override
    public Optional<Set<Client<SocketConnection>>> call() throws Exception {
        try {
            while (runningProperty.get()) {
                Socket socket = serverSocket.accept();
                Client<SocketConnection> client = new Client<>(new SocketConnection(socket));
                clients.add(client);
                clientConsumer.accept(client);
            }

            return Optional.empty();
        } catch (SocketException e) {
            return Optional.of(clients);
        }
    }
}
