package com.meti.handle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public abstract class TypeTokenHandler<T> implements TokenHandler {
    private final Class<T> clazz;

    protected TypeTokenHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean canHandle(Object token) {
        return clazz.isAssignableFrom(token.getClass());
    }

    @Override
    public void handle(Object token) {
        handleGeneric(clazz.cast(token));
    }

    protected abstract void handleGeneric(T token);
}
