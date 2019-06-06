package com.meti.app.client.chat;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedChatMessage implements Serializable, ChatMessage {
    private final String user;
    private final String value;

    public SerializedChatMessage(String user, String value) {
        this.user = user;
        this.value = value;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getValue() {
        return value;
    }
}
