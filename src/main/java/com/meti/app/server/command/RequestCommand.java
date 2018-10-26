package com.meti.app.server.command;

import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public class RequestCommand<R extends Serializable> extends ParameterizedCommand<RequestTypes, R> {
    public RequestCommand(Class<R> resultClass, RequestTypes... parameters) {
        super(parameters, resultClass);
    }

    @Override
    public boolean isReceiving() {
        return true;
    }
}
