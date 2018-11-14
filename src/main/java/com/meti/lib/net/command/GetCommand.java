package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

public class GetCommand<T, C extends Collection<T> & Serializable> extends Command<T, C> {
    public GetCommand(C collection) {
        super(collection);
    }

    @Override
    public boolean isReturning() {
        return true;
    }
}
