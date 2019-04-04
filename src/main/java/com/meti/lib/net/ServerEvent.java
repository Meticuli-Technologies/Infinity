package com.meti.lib.net;

import com.meti.lib.event.Event;

public class ServerEvent extends Event {
    public static final int ON_REGISTERED = 0;

    public ServerEvent(Object[] args) {
        super(args);
    }

    public Client<?> getClient() {
        return (Client<?>) args[1];
    }

    public Server getServer() {
        return (Server) args[0];
    }
}
