package com.meti.app.client.chat;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedChatMessage implements Serializable, ChatMessage {
    private static final long serialVersionUID = -8064488992846676572L;
    private final String value;

    public SerializedChatMessage(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
