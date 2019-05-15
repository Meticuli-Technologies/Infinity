package com.meti.net.server;

import com.meti.net.client.Client;
import com.meti.handle.TokenHandler;

import java.io.IOException;
import java.util.concurrent.Callable;

abstract class ServerHandler implements Callable<ServerHandler> {
    private final TokenHandler handler;
    private final Client client;

    ServerHandler(TokenHandler handler, Client client) {
        this.handler = handler;
        this.client = client;
    }

    @Override
    public ServerHandler call() throws Exception {
        while (client.isOpen()) {
            Object token = client.read();
            process(token);
        }
        return this;
    }

    private void process(Object token) throws IOException {
        if (handler.canHandle(token)) {
            handler.handle(token);
        }

        processImpl(token);
    }

    protected abstract void processImpl(Object token) throws IOException;
}
