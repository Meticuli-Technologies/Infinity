package com.meti.app.client;

import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerHopper;
import com.meti.lib.handle.MappedHandlerImpl;
import com.meti.lib.source.ReadableSource;

import java.io.ObjectInputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class InfinityClient extends HandlerHopper<ReadableSource<ObjectInputStream>> {
    private final MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler;

    public InfinityClient(ReadableSource<ObjectInputStream> readable, MappedHandlerImpl<Object, ReadableSource<ObjectInputStream>> handler) {
        super(readable, handler);
        this.handler = handler;
    }

    public void add(Handler<Object, ReadableSource<ObjectInputStream>> subHandler) {
        handler.add(subHandler);
    }
}
