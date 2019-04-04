package com.meti.lib.event;

import java.io.Serializable;

public class Event implements Serializable {
    protected final Object[] args;

    public Event(Object[] args) {
        this.args = args;
    }
}
