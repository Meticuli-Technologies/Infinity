package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

public class Command<T extends Serializable, P extends Collection<? extends T> & Serializable> implements Serializable {
    public final P parameters;

    Command(P collection) {
        this.parameters = collection;
    }
}
