package com.meti.lib.net;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class ClientHandler implements Runnable {
    private final Consumer<Exception> callback;
    private final Client client;

    public ClientHandler(Consumer<Exception> callback, Client client) {
        this.callback = callback;
        this.client = client;
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && !client.socket.isClosed()) {
            try {
                Command read = client.read();
                //TODO: handle read
            } catch (Exception e) {
                callback.accept(e);
            }
        }
    }
}
