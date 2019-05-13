package com.meti.net.source;

import java.io.Closeable;
import java.io.IOException;

public interface SourceSupplier extends Closeable {
    Source accept() throws IOException;

    default boolean isOpen() {
        return !isClosed();
    }

    boolean isClosed();
}
