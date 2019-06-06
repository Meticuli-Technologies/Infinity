package com.meti.app.client.chat;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface ChatMessageResponse {
    String getTimestamp();

    String getUser();

    String getValue();
}
