package com.meti.lib.net.server;

import com.meti.lib.concurrent.Listener;
import com.meti.lib.concurrent.LoopedExecutable;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ClientProcessor;
import com.meti.lib.net.client.handle.ResponseHandler;
import com.meti.lib.net.client.handle.ResponseProcessor;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public abstract class AbstractServer extends LoopedExecutable implements Server {
    private final Set<ResponseHandler> responseHandlers = new HashSet<>();
    private final Set<Client> clients = new HashSet<>();
    private Consumer<Client> onConnect;

    AbstractServer(Collection<? extends ResponseHandler> initialHandlers) {
        this.responseHandlers.addAll(initialHandlers);
    }

    @Override
    protected void loop() throws IOException {
        Client client = acceptClient();
        if(onConnect != null){
            onConnect.accept(client);
        }
        clients.add(client);
        submitClient(client);
    }

    private void submitClient(Client client) {
        ResponseProcessor processor = new ClientProcessor(client);
        processor.addHandlers(responseHandlers);

        Listener handler = new EmptyProcessorExecutable(client, processor);
        handler.listen();
    }

    @Override
    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    protected abstract Client acceptClient() throws IOException;

    @Override
    public Set<ResponseHandler> getResponseHandlers() {
        return responseHandlers;
    }

    @Override
    public void setOnConnect(Consumer<Client> onConnect) {
        this.onConnect = onConnect;
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
