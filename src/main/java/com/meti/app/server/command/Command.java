package com.meti.app.server.command;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public abstract class Command<R extends Serializable> implements Serializable {
    public final Class<R> resultClass;

    public Command(Class<R> resultClass) {
        this.resultClass = resultClass;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String toString(){
        return getName() + " " + resultClass.getSimpleName();
    }

    public abstract boolean isReceiving();
}
