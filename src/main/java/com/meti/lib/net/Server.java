package com.meti.lib.net;

import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public abstract class Server<S extends CompoundSource<?, ?>, O extends SourceSupplier<S>> implements Callable<O>, Closeable {
    private final Set<S> sources = new HashSet<>();
    private final O supplier;

    public Server(O supplier) {
        this.supplier = supplier;
    }

    @Override
    public O call() throws Exception {
        while (supplier.isOpen()) {
            //noinspection unchecked
            S next = supplier.next();
            sources.add(next);
            handle(next);
        }
        return supplier;
    }

    protected abstract void handle(S next);

    @Override
    public void close() throws IOException {
        supplier.close();
        for (S source : sources) {
            source.close();
        }
    }
}