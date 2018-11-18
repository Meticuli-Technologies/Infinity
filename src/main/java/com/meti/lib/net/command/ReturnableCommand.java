package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public abstract class ReturnableCommand<T extends Serializable, P extends Collection<? extends T> & Serializable, R> extends Command<T, P> {
    public final Class<R> returnClass;

    ReturnableCommand(P parameters, Class<R> returnClass) {
        super(parameters);
        this.returnClass = returnClass;
    }
}
