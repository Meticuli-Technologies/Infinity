package com.meti.app.control;

import com.meti.lib.net.query.OKResponse;
import com.meti.lib.net.query.Query;

public class Message implements Query<OKResponse> {
    private final String user;
    private final String value;

    public Message(String user, String value) {
        this.user = user;
        this.value = value;
    }

    @Override
    public Class<OKResponse> getReturnClass() {
        return OKResponse.class;
    }
}
