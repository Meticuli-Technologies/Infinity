package com.meti;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private final String value;

    public ChatMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
