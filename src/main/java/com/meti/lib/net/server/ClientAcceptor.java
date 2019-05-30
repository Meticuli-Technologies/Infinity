package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class ClientAcceptor implements Callable<Void>, Stoppable {
    private final Set<? extends ResponseHandler> handlers;
    private boolean running;

    protected ClientAcceptor(Set<? extends ResponseHandler> handlers) {
        this.handlers = handlers;
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
        client.getHandlers().addAll(handlers);
        submitHandler(new ClientHandler(client));
    }

    protected abstract void submitHandler(Callable<Void> handler);

    @Override
    public void stop() {
        running = false;
    }
}
