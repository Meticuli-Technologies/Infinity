package com.meti.lib.fx;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Wizard<P, R> {
    Optional<String> getName();

    void open(P... parameters);
    boolean isRunning();
    void close();

    R getResult() throws IOException;
}
