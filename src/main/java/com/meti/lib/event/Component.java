package com.meti.lib.event;

import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;

import java.util.stream.Stream;

public abstract class Component<E extends Event, T> {
    public final EventManager<E, T> eventManager = new EventManager<>();

    public abstract Stream<? extends Handler<Object>> getHandlers(Client client);
}
