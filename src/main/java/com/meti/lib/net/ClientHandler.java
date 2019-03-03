package com.meti.lib.net;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public class ClientHandler implements Runnable {
    private final Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    public void run() {

    }
}
