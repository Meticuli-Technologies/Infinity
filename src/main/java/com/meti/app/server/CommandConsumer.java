package com.meti.app.server;

import com.meti.app.server.command.Command;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public abstract class CommandConsumer<T extends Command, R extends Serializable> {
    public R processObject(Object obj, Server server) {
        return process(getCommandClass().cast(obj), server);
    }

    protected abstract R process(T command, Server server);

    protected abstract Class<T> getCommandClass();
}
