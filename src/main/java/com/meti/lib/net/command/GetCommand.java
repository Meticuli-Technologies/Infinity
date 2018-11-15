package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

public class GetCommand<T extends Serializable, P extends Collection<T> & Serializable, R> extends ReturnableCommand<T, P, R> {
    public GetCommand(P parameters, Class<R> returnClass) {
        super(parameters, returnClass);
    }
}
