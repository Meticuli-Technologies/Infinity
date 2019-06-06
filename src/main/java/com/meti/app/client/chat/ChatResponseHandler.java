package com.meti.app.client.chat;

import com.meti.lib.net.StringTypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ChatResponseHandler extends StringTypeHandler {
    private final Server server;

    public ChatResponseHandler(Server server) {
        this.server = server;
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
