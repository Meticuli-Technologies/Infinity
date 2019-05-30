package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Client extends Closeable {
    default void writeAndFlush(Serializable message) throws IOException {
        write(message);
        flush();
    }

    void processNextResponse() throws Throwable;

    Set<ResponseHandler> getHandlers();

    void write(Serializable serializable) throws IOException;

    void flush() throws IOException;
}
