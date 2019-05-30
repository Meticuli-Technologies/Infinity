package com.meti.lib.net.client;

import com.meti.lib.net.ComplexCloseable;
import com.meti.lib.net.handle.ResponseHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface Client extends ComplexCloseable {
    String getName();

    default void writeAndFlush(Serializable message) throws IOException {
        write(message);
        flush();
    }

    void processNextResponse() throws Throwable;

    void writeAndFlushIterable(Iterable<? extends Serializable> collection) throws IOException;

    Set<ResponseHandler> getHandlers();

    @Override
    default boolean isOpen() {
        return !isClosed();
    }

    void write(Serializable serializable) throws IOException;

    void flush() throws IOException;
}
