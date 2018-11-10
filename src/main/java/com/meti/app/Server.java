package com.meti.app;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/10/2018
 */
public class Server {
    private final BooleanProperty runningProperty = new SimpleBooleanProperty(true);

    private final ExecutorService service;
    private final ServerSocket serverSocket;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.service = Executors.newCachedThreadPool();
    }

    public void listen() {
        service.submit(new ServerListener());
    }

    private class ServerListener implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            return null;
        }
    }
}
