package com.meti;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class Client<S extends Source<?, ?>> implements Closeable {
    protected final S source;

    public Client(S source) {
        this.source = source;
    }

    public void flush() throws IOException {
        source.getOutputStream().flush();
    }

    public int read() throws IOException, ClassNotFoundException {
        return source.getInputStream().read();
    }

    public void write(int b) throws IOException {
        source.getOutputStream().write(b);
    }

    @Override
    public void close() throws IOException {
        source.close();
    }
}
