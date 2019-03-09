package com.meti.lib.handle;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/9/2019
 */
public class CompoundHandler<T> implements Handler<T> {
    private final HandlerMap<T, Handler<T>> map = new HandlerMap<>();

    @Override
    public void accept(T t) {
    }

    @Override
    public boolean test(T t) {
        return false;
    }
}
