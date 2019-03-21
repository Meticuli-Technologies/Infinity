package com.meti.app.net;

import com.meti.app.feature.Message;
import com.meti.lib.net.Update;

import java.util.ArrayList;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class Chat {
    private final ArrayList<Message> content = new ArrayList<>();

    public ChatUpdate add(Message message) {
        content.add(message);

        return new ChatUpdate(message, true, false);
    }

    public static class ChatUpdate implements Update {
        private final Message message;
        private final boolean wasAdded;
        private final boolean wasRemoved;

        public ChatUpdate(Message message, boolean wasAdded, boolean wasRemoved) {
            this.message = message;
            this.wasAdded = wasAdded;
            this.wasRemoved = wasRemoved;
        }
    }
}
