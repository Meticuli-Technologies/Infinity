package com.meti;

import java.io.Closeable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/30/2019
 */
public interface ComplexCloseable extends Closeable {
    boolean isOpen();

    boolean isClosed();
}
