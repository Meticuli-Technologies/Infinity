package com.meti.app;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class Message implements Serializable {
    private final String content;

    public Message(String content) {
        this.content = content;
    }
}
