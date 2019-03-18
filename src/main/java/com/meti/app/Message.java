package com.meti.app;

import com.meti.lib.OKResponse;
import com.meti.lib.Respondable;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class Message implements Serializable, Respondable<OKResponse> {
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    @Override
    public Class<? extends OKResponse> getResponseClass() {
        return OKResponse.class;
    }
}
