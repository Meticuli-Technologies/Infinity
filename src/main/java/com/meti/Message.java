package com.meti;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Message implements Serializable {
    private final String user;
    private final String value;

    public Message(String user, String value) {
        this.user = user;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + user + "]: " + value;
    }
}
