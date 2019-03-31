package com.meti;

import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public abstract class Server<S extends Source<?, ?>, C extends Client<S>> implements Callable<Void> {
    private final SourceSupplier<S> sourceSupplier;
    private final Function<S, C> clientConverter;

    public Server(Function<S, C> clientConverter, SourceSupplier<S> sourceSupplier) {
        this.clientConverter = clientConverter;
        this.sourceSupplier = sourceSupplier;
    }

    @Override
    public Void call() {
        while (!sourceSupplier.isClosed()) {
            accept(clientConverter.apply(sourceSupplier.get()));
        }

        return null;
    }

    public abstract void accept(C client);
}
