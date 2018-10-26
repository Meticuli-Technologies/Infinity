package com.meti.app.server.command;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public abstract class ParameterizedCommand<P extends Serializable, R extends Serializable> extends Command<R> {
    private final P[] parameters;

    public ParameterizedCommand(P[] parameters, Class<R> resultClass) {
        super(resultClass);
        this.parameters = parameters;
    }

    public P[] getParameters() {
        return parameters;
    }
}
