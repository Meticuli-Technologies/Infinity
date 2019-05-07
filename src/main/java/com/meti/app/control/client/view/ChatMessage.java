package com.meti.app.control.client.view;

import com.meti.lib.io.query.Query;
import com.meti.lib.io.respond.OKResponse;

import java.io.Serializable;

public class ChatMessage implements Query<OKResponse>, Serializable {
    private final String value;

    public ChatMessage(String value) {
        this.value = value;
    }

    @Override
    public Class<OKResponse> getTypeClass() {
        return OKResponse.class;
    }

    public String getValue() {
        return value;
    }
}
