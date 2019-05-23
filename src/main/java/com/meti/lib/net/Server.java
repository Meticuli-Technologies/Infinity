package com.meti.lib.net;

import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public abstract class Server<S extends CompoundSource<?, ?>, O extends SourceSupplier<S>> implements ServerImpl<S, O> {
    protected final O supplier;
    private final Collection<S> sources = new HashSet<>();
    private Consumer<S> onAccept;

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

    @Override
    public Collection<S> getClients() {
        return Collections.unmodifiableCollection(sources);
    }

    @Override
    public Consumer<S> getOnAccept() {
        return onAccept;
    }

    @Override
    public void setOnAccept(Consumer<S> onAccept) {
        this.onAccept = onAccept;
    }
}