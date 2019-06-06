package com.meti.app.client;

import com.meti.lib.net.client.handle.ResponseHandler;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public abstract class TypeHandler<T> implements ResponseHandler {
    private final Class<T> tClass;

    public TypeHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public boolean canHandle(Object response) {
        return tClass.isInstance(response);
    }
}
