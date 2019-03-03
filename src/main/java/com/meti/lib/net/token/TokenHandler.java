package com.meti.lib.net.token;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/3/2019
 */
public abstract class TokenHandler<T> implements Consumer<T> {
    private final Class<T> tClass;

    public TokenHandler(Class<T> tClass) {
        this.tClass = tClass;
    }
}
