package com.meti.app;

import com.meti.lib.OKResponse;
import com.meti.lib.Respondable;
import com.meti.lib.Update;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class Message implements Serializable, Respondable<OKResponse> {
    public final String content;

    public Message(String content) {
        this.content = content;
    }

    @Override
    public Class<? extends OKResponse> getResponseClass() {
        return OKResponse.class;
    }

    public static class MessageUpdate implements Update {
        public final User user;
        public final Message message;

        public MessageUpdate(User user, Message message) {
            this.user = user;
            this.message = message;
        }
    }
}
