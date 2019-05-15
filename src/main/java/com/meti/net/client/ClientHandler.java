package com.meti.net.client;

import com.meti.concurrent.ExecutorServiceManager;
import com.meti.handle.TokenHandler;

import java.util.concurrent.Callable;

public class ClientHandler implements Callable<ClientHandler> {
    private final Client client;
    private final TokenHandler handler;

    public ClientHandler(Client client, TokenHandler handler) {
        this.client = client;
        this.handler = handler;
    }

    @Override
    public ClientHandler call() throws Exception {
        while (client.isOpen()) process(client.read());
        return null;
    }

    private void process(Object token) {
        if (handler.canHandle(token)) handler.handle(token);
        else throw new IllegalStateException("Unknown token: " + token.toString());
    }

    public Client getClient() {
        return client;
    }

    public void listen(ExecutorServiceManager manager) {
        manager.submit(this);
    }
}
