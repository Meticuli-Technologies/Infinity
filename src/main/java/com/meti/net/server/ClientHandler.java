package com.meti.net.server;

import com.meti.concurrent.executable.LoopedExecutable;
import com.meti.net.client.Client;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ClientHandler extends LoopedExecutable {
    private final Client client;

    public ClientHandler(Client client) {
        this.client = client;
    }

    @Override
    protected void loop() {
        processNextResponseOrThrow();
    }

    @Override
    public void close() throws IOException {
        super.close();
        client.close();
    }

    private void processNextResponseOrThrow() {
        try {
            client.processNextResponse();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
