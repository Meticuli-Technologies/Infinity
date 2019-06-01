package com.meti.lib.net.server;

import com.meti.lib.concurrent.Listener;
import com.meti.lib.concurrent.LoopedExecutable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseProcessor;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class AbstractServer extends LoopedExecutable implements Server {
    private final Collection<ResponseHandler> handlers = new HashSet<>();
    private final Collection<Client> clients = new HashSet<>();

    AbstractServer(Collection<? extends ResponseHandler> initialHandlers) {
        this.handlers.addAll(initialHandlers);
    }

    @Override
    protected void loop() throws IOException {
        Client client = acceptClient();
        clients.add(client);
        submitClient(client);
    }

    protected abstract Client acceptClient() throws IOException;

    private void submitClient(Client client) {
        ResponseProcessor processor = new ClientProcessor(client);
        processor.addHandlers(handlers);

        Listener handler = new ProcessorExecutable(client, processor);
        handler.listen();
    }

    @Override
    protected void preClose() throws IOException {
        stop();
        closeClients();
    }

    private void closeClients() throws IOException {
        for (Client client : clients) {
            client.close();
        }
    }
}
