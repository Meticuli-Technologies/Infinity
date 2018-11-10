package com.meti.lib.net;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Server {
    private final BooleanProperty runningProperty = new SimpleBooleanProperty(true);
    private final ClientConsumer clientConsumer;
    public final ServerSocket serverSocket;
    private final ExecutorService service;

    private Future<Set<Client>> future;

    public Server(int port, ClientConsumer clientConsumer) throws IOException {
        this.service = Executors.newCachedThreadPool();
        this.serverSocket = new ServerSocket(port);
        this.clientConsumer = clientConsumer;
    }

    public ServerListener listen() {
        ServerListener listener = new ServerListener(clientConsumer, serverSocket);
        listener.runningProperty.bindBidirectional(runningProperty);
        future = service.submit(listener);
        return listener;
    }

    public Optional<Set<Client>> stop() throws Exception {
        return stop(Duration.ofSeconds(1));
    }

    public Optional<Set<Client>> stop(Duration duration) throws Exception {
        runningProperty.set(false);
        service.shutdown();

        try {
            return Optional.ofNullable(future.get(duration.toMillis(), TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            /*
            even though we caught an exception, we should shutdown the service
            then send it back up to the user
            can't use finally because if the try block is successful
            the service will be shutdown properly and ExecutorService.shutdownNow() becomes redundant
            */
            service.shutdownNow();
            throw e;
        }
    }
}
