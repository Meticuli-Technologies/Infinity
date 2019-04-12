package com.meti.chat;

import com.meti.app.User;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Message implements Serializable {
    private final User user;
    private final String value;

    public Message(User user, String value) {
        this.user = user;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + user + "]: " + value;
    }
}
