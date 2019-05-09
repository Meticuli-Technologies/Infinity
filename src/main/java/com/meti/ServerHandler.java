package com.meti;

import java.io.IOException;
import java.util.concurrent.Callable;

class ServerHandler implements Callable<ServerHandler> {
    private final TokenHandler handler;
    private final Client client;

    public ServerHandler(TokenHandler handler, Source source) throws IOException {
        this.handler = handler;
        this.client = new Client(source);
    }

    @Override
    public ServerHandler call() throws Exception {
        while (client.isOpen()) {
            Object token = client.read();
            Object toWrite = handle(token);
            client.write(toWrite);
            client.flush();
        }
        return this;
    }

    public Object handle(Object token) {
        if (handler.canHandle(token)) {
            return handler.handle(token);
        } else {
            return new IllegalArgumentException("Cannot handle " + token.toString());
        }
    }
}
