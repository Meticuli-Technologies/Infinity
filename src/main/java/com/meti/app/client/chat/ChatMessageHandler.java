package com.meti.app.client.chat;

import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.server.Server;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public class ChatMessageHandler extends TypeHandler<ChatMessage> {
    private final Server server;

    public ChatMessageHandler(Server server) {
        super(ChatMessage.class);
        this.server = server;
    }

    @Override
    public Optional<Serializable> handleGeneric(ChatMessage response, Client client) {
        Serializable result = constructResult(response, client);
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

    private Serializable constructResult(ChatMessage response, Client client) {
        String timestamp = Date.from(Instant.now()).toString();
        return new SerializedChatMessageResponse(timestamp, client.getName(), response.getValue());
    }
}
