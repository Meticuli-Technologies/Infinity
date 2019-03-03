package com.meti.lib.net;

import com.meti.lib.TryableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
        if (!clients.isEmpty()) {
            TryableFactory factory = new TryableFactory();
            try {
                Set<Socket> collect = factory.checkAll(clients.stream()
                        .map(client -> client.socket)
                        .peek(factory.accept(Socket::close))
                ).collect(Collectors.toSet());

                if (collect.isEmpty()) {
                    throw new IllegalStateException("There are clients present, yet no sockets were closed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        serverSocket.close();
    }

    public void listen(Consumer<Runnable> runnableConsumer, Consumer<Exception> callback) {
        runnableConsumer.accept(() -> {
            while (!Thread.interrupted()) {
                try {
                    Client client = new Client(serverSocket.accept());
                    runnableConsumer.accept(new ClientHandler(callback, client));

                    clients.add(client);
                } catch (IOException e) {
                    callback.accept(e);
                }
            }
        });
    }
}
