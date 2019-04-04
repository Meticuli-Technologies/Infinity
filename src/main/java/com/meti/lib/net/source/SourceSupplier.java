package com.meti.lib.net.source;

import java.io.Closeable;
import java.util.function.Supplier;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public interface SourceSupplier<S extends Source<?, ?>> extends Supplier<S>, Closeable {
    boolean isClosed();
}
