package com.meti.lib.event;

public class Component<E extends Event> {
    public final EventManager<E> eventManager = new EventManager<>();
}
