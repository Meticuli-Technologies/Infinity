package com.meti.app.client.chat;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedChatMessageResponse implements ChatMessageResponse, Serializable {
    private static final long serialVersionUID = 5032758354050221450L;
    private final String timestamp;
    private final String user;
    private final String value;

    public SerializedChatMessageResponse(String timestamp, String user, String value) {
        this.timestamp = timestamp;
        this.user = user;
        this.value = value;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public String getValue() {
        return value;
    }
}
