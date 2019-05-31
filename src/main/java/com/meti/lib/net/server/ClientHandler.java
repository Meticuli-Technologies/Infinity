package com.meti.lib.net.server;

import com.meti.lib.concurrent.executable.LoopedExecutable;
import com.meti.lib.net.client.Client;

import java.io.IOException;

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
