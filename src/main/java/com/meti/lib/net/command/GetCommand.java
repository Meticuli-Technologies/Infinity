package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

public class GetCommand<T extends Serializable, C extends Collection<T> & Serializable, R> extends ReturnableCommand<T, C, R> {
    public GetCommand(C collection, Class<R> returnClass) {
        super(collection, returnClass);
    }
}
