package com.meti.lib.net;

import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerMap;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/9/2019
 */
public class TokenHandler implements Handler<Object> {
    private final HandlerMap<Object, Handler<Object>> map = new HandlerMap<>();

    @Override
    public void accept(Object o) {
    }

    @Override
    public boolean test(Object o) {
        return false;
    }
}
