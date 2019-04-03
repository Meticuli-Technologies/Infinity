package com.meti.app.server;

import com.meti.app.control.Message;
import com.meti.lib.event.Event;

public class ChatEvent extends Event {
    public static final int ADDED = 0;
    public static final int REMOVED = 1;

    public ChatEvent(Message message, boolean wasAdded, boolean wasRemoved) {
        super(new Object[]{message, wasAdded, wasRemoved});
    }

    public Message getMessage() {
        return (Message) args[0];
    }

    public boolean wasAdded() {
        return (boolean) args[1];
    }

    public boolean wasRemoved() {
        return (boolean) args[2];
    }
}
