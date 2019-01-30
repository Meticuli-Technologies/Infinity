package com.meti.lib.fx;

import java.io.IOException;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Wizard<T> {
    Optional<String> getName();

    void open();
    boolean isRunning();
    void close();

    T getResult() throws IOException;
}
