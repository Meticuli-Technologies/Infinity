package com.meti.lib.source;

import java.io.Closeable;

public interface Source extends Closeable {
    boolean isClosed();

    default boolean isOpen() {
        return !isClosed();
    }
}
