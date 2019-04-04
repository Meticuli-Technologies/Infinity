package com.meti.lib.net;

import com.meti.lib.net.source.Source;

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

    @Override
    public void close() throws IOException {
        source.close();
    }

    public void flush() throws IOException {
        source.getOutputStream().flush();
    }

    public String getName() {
        return source.getName();
    }

    public boolean isOpen() {
        return !source.isClosed();
    }

    public int read() throws IOException {
        return source.getInputStream().read();
    }

    public void write(int b) throws IOException {
        source.getOutputStream().write(b);
    }
}
