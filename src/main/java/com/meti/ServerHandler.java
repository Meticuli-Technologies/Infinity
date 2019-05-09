package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

class ServerHandler implements Callable<ServerHandler> {
    private final TokenHandler handler;
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public ServerHandler(TokenHandler handler, Socket socket) throws IOException {
        this.handler = handler;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public ServerHandler call() throws Exception {
        while (!socket.isClosed()) {
            Object token = inputStream.readUnshared();
            Object toWrite = handle(token);
            outputStream.writeUnshared(toWrite);
            outputStream.flush();
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
