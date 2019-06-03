package com.meti.app;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseHandler;
import com.meti.lib.net.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class StringResponseHandler implements ResponseHandler {
    private final Server server;

    public StringResponseHandler(Server server) {
        this.server = server;
    }

    @Override
    public boolean canHandle(Object response) {
        return response instanceof String;
    }

    @Override
    public Optional<Serializable> handle(Object response, Client client) {
        String result = client.getName() + ": " + response;
        for (Client serverClient : server.getClients()) {
            try {
                serverClient.writeAndFlush(result);
            } catch (IOException e) {
                //TODO: handle e
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
