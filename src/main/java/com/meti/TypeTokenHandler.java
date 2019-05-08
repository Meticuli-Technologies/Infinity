package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
abstract class TypeTokenHandler<T> implements TokenHandler {
    private final Class<T> clazz;

    private TypeTokenHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean canHandle(Object token) {
        return clazz.isAssignableFrom(token.getClass());
    }

    @Override
    public Object handle(Object token) {
        return handleGeneric(clazz.cast(token));
    }

    protected abstract boolean handleGeneric(T token);
}
