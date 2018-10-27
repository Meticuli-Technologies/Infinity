package com.meti.app.server;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/26/2018
 */
public abstract class TokenHandler<T> {
    public final Class<T> tokenClass;

    public TokenHandler(Class<T> tokenClass) {
        this.tokenClass = tokenClass;
    }

    public Optional<?> handleObject(Object obj, Server server) {
        if (tokenClass.isAssignableFrom(obj.getClass())) {
            return Optional.ofNullable(handleToken(tokenClass.cast(obj), server));
        }
        return Optional.empty();
    }

    protected abstract Object handleToken(T token, Server server);
}
