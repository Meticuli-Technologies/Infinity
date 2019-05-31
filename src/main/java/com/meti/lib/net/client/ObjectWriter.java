package com.meti.lib.net.client;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/31/2019
 */
public interface ObjectWriter {
    default void writeAndFlush(Serializable message) throws IOException {
        write(message);
        flush();
    }

    void write(Serializable serializable) throws IOException;

    void flush() throws IOException;

    default void writeAndFlushIterable(Iterable<? extends Serializable> collection) throws IOException {
        for (Serializable serializable : collection) {
            write(serializable);
        }

        flush();
    }
}
