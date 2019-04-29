package com.meti.lib.io;

import java.util.function.Supplier;

public interface SourceSupplier<S extends Source> extends Supplier<S> {
    boolean isClosed();

    boolean isOpen();
}
