package com.meti.lib.net.command;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public abstract class ReturnableCommand<T extends Serializable, C extends Collection<? extends T> & Serializable, R> extends Command<T, C> {
    public final Class<R> returnClass;

    public ReturnableCommand(C collection, Class<R> returnClass) {
        super(collection);
        this.returnClass = returnClass;
    }
}
