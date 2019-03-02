package com.meti.lib.net;

import com.meti.lib.Callback;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/2/2019
 */
public class Server implements Closeable {
    private final ServerSocket serverSocket;
    private final ObservableList<Client> clients = FXCollections.observableArrayList();

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    public void listen(ExecutorService service, Callback callback) {
        service.submit(() -> {
            while (!Thread.interrupted()) {
                try {
                    clients.add(new Client(serverSocket.accept());
                } catch (IOException e) {
                    callback.accept(e);
                }
            }
        });
    }
}
