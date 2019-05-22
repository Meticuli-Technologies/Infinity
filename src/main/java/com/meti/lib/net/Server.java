package com.meti.lib.net;

import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public abstract class Server<S extends CompoundSource<?, ?>, O extends SourceSupplier<S>> implements ServerImpl<S, O> {
    private final Collection<S> sources = new HashSet<>();
    protected final O supplier;

    private Consumer<S> onAccept;

    @Override
    public Consumer<S> getOnAccept() {
        return onAccept;
    }

    @Override
    public void setOnAccept(Consumer<S> onAccept) {
        this.onAccept = onAccept;
    }

    protected Server(O supplier) {
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