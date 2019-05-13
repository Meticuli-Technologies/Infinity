package com.meti.net.server;

import com.meti.net.source.Source;
import com.meti.net.source.SourceSupplier;
import com.meti.net.client.Client;
import com.meti.concurrent.ExecutorServiceManager;
import com.meti.handle.TokenHandler;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Server implements Callable<Server>, Closeable {
    private final List<Client> clients = new ArrayList<>();
    private final ExecutorServiceManager manager;
    private final SourceSupplier sourceSupplier;
    private final TokenHandler handler;

    public Server(SourceSupplier sourceSupplier, ExecutorServiceManager manager, TokenHandler handler) {
        this.sourceSupplier = sourceSupplier;
        this.manager = manager;
        this.handler = handler;
    }

    @Override
    public Server call() throws Exception {
        while (sourceSupplier.isOpen()) {
            Client client = acceptClient();
            manager.submit(new ReflectionHandler(handler, client));
        }
        return this;
    }

    private Client acceptClient() throws IOException {
        Source accepted = sourceSupplier.accept();
        Client client = new Client(accepted);
        clients.add(client);
        return client;
    }

    @Override
    public void close() throws IOException {
        sourceSupplier.close();
    }

    public SourceSupplier getSourceSupplier() {
        return sourceSupplier;
    }

    public Server listen() {
        manager.submit(this);
        return this;
    }

    private class ReflectionHandler extends ServerHandler {
        ReflectionHandler(TokenHandler handler, Client client) {
            super(handler, client);
        }

        @Override
        public void processImpl(Object token) throws IOException {
            for (Client client : clients) {
                client.write(token);
                client.flush();
            }
        }
    }
}