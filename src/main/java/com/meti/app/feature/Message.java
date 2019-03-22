package com.meti.app.feature;

import com.meti.app.User;

import java.io.Serializable;

public class Message implements Serializable {
    public final String content;
    private final User user;

    public Message(String content, User user) {
        this.content = content;
        this.user = user;
    }
}
