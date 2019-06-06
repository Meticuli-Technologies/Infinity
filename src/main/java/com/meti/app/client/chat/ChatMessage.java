package com.meti.app.client.chat;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class ChatMessage implements Serializable {
    private final String user;
    private final String value;

    public ChatMessage(String user, String value) {
        this.user = user;
        this.value = value;
    }
}
