package com.meti.app.client;

import com.meti.lib.handle.Handler;
import com.meti.lib.net.Listener;
import com.meti.lib.source.ReadableSource;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface ClientImpl extends Closeable, Listener {
    void flush() throws IOException;

    Object read() throws IOException, ClassNotFoundException;

    void write(Serializable serializable) throws IOException;

    void add(Handler<Object, ReadableSource<ObjectInputStream>> subHandler);

    @Override
    abstract void listen();
}
