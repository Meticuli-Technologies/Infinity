package com.meti;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class Client<S extends Source<?, ?>> implements Closeable {
    private final S source;

    public Client(S source) {
        this.source = source;
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}
