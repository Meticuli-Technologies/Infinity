package com.meti.app.server;

import com.meti.app.control.Message;
import com.meti.lib.event.Event;

public class ChatEvent extends Event {
    public static final int ALL = -1;
    public static final int ADDED = 0;
    public static final int REMOVED = 1;

    public ChatEvent(Message message) {
        super(new Object[]{message});
    }

    public Message getMessage() {
        return (Message) args[0];
    }
}
