package com.meti.lib.net;

import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseHandler;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public abstract class TypeHandler<T> implements ResponseHandler {
    private final Class<T> tClass;

    protected TypeHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public boolean canHandle(Object response) {
        return tClass.isInstance(response);
    }

    @Override
    public Optional<Serializable> handle(Object response, Client client) {
        return handleGeneric(tClass.cast(response), client);
    }

    public abstract Optional<Serializable> handleGeneric(T response, Client client);
}
