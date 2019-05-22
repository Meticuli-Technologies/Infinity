package com.meti.lib.handle;

import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

public class HandlerHopper<R extends ReadableSource<ObjectInputStream>> extends Hopper<R> {
    private final Handler<Object, R> handler;

    public HandlerHopper(R readable, Handler<Object, R> handler) {
        super(readable);
        this.handler = handler;
    }

    @Override
    protected void handle(Object token, R readable) {
        handler.handle(token, readable);
    }
}
