package com.meti.app.feature;

import com.meti.app.User;
import com.meti.lib.net.Query;
import com.meti.lib.respond.OKResponse;
import com.meti.lib.respond.Respondable;
import com.meti.lib.net.Update;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/18/2019
 */
public class Message extends Query<OKResponse> implements Serializable, Respondable<OKResponse> {
    public final String content;

    public Message(String content) {
        super(OKResponse.class);
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
