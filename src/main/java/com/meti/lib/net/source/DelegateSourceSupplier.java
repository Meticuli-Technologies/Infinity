package com.meti.lib.net.source;

import java.io.IOException;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class DelegateSourceSupplier<S extends Source<?, ?>> implements SourceSupplier<S> {
    private final SourceSupplier<? extends Source<?, ?>> supplier;
    private final Function<Source<?, ?>, S> sourceConverter;

    public DelegateSourceSupplier(SourceSupplier<? extends Source<?, ?>> supplier, Function<Source<?, ?>, S> sourceConverter) {
        this.supplier = supplier;
        this.sourceConverter = sourceConverter;
    }

    @Override
    public boolean isClosed() {
        return supplier.isClosed();
    }

    @Override
    public void close() throws IOException {
        supplier.close();
    }

    @Override
    public S get() {
        return sourceConverter.apply(supplier.get());
    }
}
