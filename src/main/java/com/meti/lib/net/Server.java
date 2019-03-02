package com.meti.lib.net;

import com.meti.lib.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
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

    public void listen(ExecutorService service, Consumer<Exception> callback) {
        service.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    clients.add(new Client(serverSocket.accept()));

                    //TODO: start individual client threads
                } catch (IOException e) {
                    callback.accept(e);
                }
            }
        });
    }
}
