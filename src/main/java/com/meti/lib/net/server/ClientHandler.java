package com.meti.lib.net.server;

import com.meti.lib.net.client.Client;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
class ClientHandler implements Callable<Void> {
    private final Client client;

    ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public Void call() throws Exception {
        while (client.isOpen()) {
            try {
                client.processNextResponse();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        client.close();
        return null;
    }
}
