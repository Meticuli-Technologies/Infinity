package com.meti;

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
        while (client.isOpen()) {
            Object token = client.read();
            if (handler.canHandle(token)) {
                handler.handle(token);
            } else {
                throw new IllegalStateException("Unknown token: " + token.toString());
            }
        }
        return null;
    }

    public Client getClient() {
        return client;
    }

    public ClientHandler listen(ExecutorServiceManager manager) {
        manager.submit(this);
        return this;
    }
}
