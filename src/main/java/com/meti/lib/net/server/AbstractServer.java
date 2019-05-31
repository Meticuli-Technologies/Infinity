package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class AbstractServer extends LoopedExecutable implements Server {
    private final Collection<ResponseHandler> handlers = new HashSet<>();
    private final Collection<Client> clients = new HashSet<>();

    protected AbstractServer(Collection<? extends ResponseHandler> initialHandlers) {
        this.handlers.addAll(initialHandlers);
    }

    @Override
    protected void loop() throws IOException {
        Client client = acceptClient();
        submitClient(client);
    }

    protected abstract Client acceptClient() throws IOException;

    private void submitClient(Client client) {
        clients.add(client);
        client.getHandlers().addAll(handlers);
        submitHandler(new ClientHandler(client));
    }

    @Override
    public void close() throws IOException {
        stop();
        closeClients();
        super.close();
    }

    private void submitHandler(Callable<Void> handler) {
        getInternalService().submit(handler);
    }

    private void closeClients() throws IOException {
        for (Client client : clients) {
            client.close();
        }
    }
}
