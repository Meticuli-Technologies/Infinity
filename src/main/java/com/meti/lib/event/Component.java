package com.meti.lib.event;

public class Component<E extends Event, T> {
    public final EventManager<E, T> eventManager = new EventManager<>();
}
