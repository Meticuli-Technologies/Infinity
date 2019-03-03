package com.meti.lib.net;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/2/2019
 */
public class Server implements Closeable {
    private final ServerSocket serverSocket;
    public final ObservableList<Client> clients = FXCollections.observableArrayList();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    public void listen(Consumer<Runnable> runnableConsumer, Consumer<Exception> callback) {
        runnableConsumer.accept(() -> {
            while (!Thread.interrupted()) {
                try {
                    Client client = new Client(serverSocket.accept());
                    runnableConsumer.accept(new ClientHandler(client));

                    clients.add(client);
                } catch (IOException e) {
                    callback.accept(e);
                }
            }
        });
    }
}
