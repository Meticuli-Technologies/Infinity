package com.meti.app.server;

import com.meti.lib.handle.Handler;
import com.meti.lib.handle.HandlerHopper;
import com.meti.lib.handle.MappedHandler;
import com.meti.lib.net.ExecutorServer;
import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.ObjectSource;
import com.meti.lib.source.SourceSupplier;

import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class InfinityServer extends ExecutorServer<CompoundSource<?, ?>, SourceSupplier<CompoundSource<?, ?>>> {
    private final Handler<Object, ObjectSource> serverHandler = new MappedHandler<>();

    public InfinityServer(SourceSupplier<CompoundSource<?, ?>> supplier) {
        super(supplier);
    }

    @Override
    public Callable<?> buildHopper(CompoundSource<?, ?> next) {
        return new HandlerHopper<>(new ObjectSource(next), serverHandler);
    }
}
