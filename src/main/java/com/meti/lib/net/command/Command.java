package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

public abstract class Command<T, C extends Collection<? extends T> & Serializable> implements Serializable {
    public final C collection;

    public Command(C collection) {
        this.collection = collection;
    }

    public abstract boolean isReturning();
}
