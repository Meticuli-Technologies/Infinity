package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class ClientAcceptorImpl implements ClientAcceptor {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1L);
    private final ExecutorService internalService = Executors.newCachedThreadPool();
    private final Collection<ResponseHandler> handlers = new HashSet<>();
    private final Collection<Client> clients = new HashSet<>();
    private final Terminator serviceTerminator;
    private boolean running;

    protected ClientAcceptorImpl(Collection<? extends ResponseHandler> initialHandlers) {
        this.handlers.addAll(initialHandlers);
        this.serviceTerminator = new ServiceTerminator(internalService);
    }

    @Override
    public void listen() {
        internalService.submit(this);
    }

    @Override
    public Void call() throws Exception {
        running = true;
        while (running) {
            Client client = acceptClient();
            submitClient(client);
        }
        return null;
    }

    protected abstract Client acceptClient() throws IOException;

    private void submitClient(Client client) {
        clients.add(client);
        client.getHandlers().addAll(handlers);
        submitHandler(new ClientHandler(client));
    }

    protected void submitHandler(Callable<Void> handler) {
        internalService.submit(handler);
    }

    @Override
    public void close() throws IOException {
        stop();
        closeClients();
        try {
            serviceTerminator.terminate(DEFAULT_TIMEOUT);
        } catch (InterruptedException e) {
            throw new IOException("Failed to terminate service.", e);
        }
    }

    @Override
    public void stop() {
        running = false;
    }

    private void closeClients() throws IOException {
        for (Client client : clients) {
            client.close();
        }
    }
}
