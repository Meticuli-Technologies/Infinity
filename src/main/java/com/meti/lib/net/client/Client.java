package com.meti.lib.net.client;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Client extends Closeable {
    String getName();

    Object read() throws IOException, ClassNotFoundException;

    default void writeAndFlush(Serializable message) throws IOException {
        write(message);
        flush();
    }

    void writeAndFlushIterable(Iterable<? extends Serializable> collection) throws IOException;

    void write(Serializable serializable) throws IOException;

    void flush() throws IOException;
}
