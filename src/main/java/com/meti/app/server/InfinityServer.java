package com.meti.app.server;

import com.meti.lib.handle.HandlerHopper;
import com.meti.lib.handle.MappedHandler;
import com.meti.lib.handle.MappedHandlerImpl;
import com.meti.lib.net.ExecutorServer;
import com.meti.lib.source.*;

import java.io.ObjectInputStream;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class InfinityServer extends ExecutorServer<CompoundSource<?, ?>, PortSourceSupplier> implements MappedHandlerProvider {
    private final MappedHandlerImpl<Object, ObjectSourceImpl> serverHandler = new MappedHandler<>();

    public InfinityServer(PortSourceSupplier supplier) {
        super(supplier);
    }

    @Override
    public Callable<?> buildHopper(CompoundSource<?, ?> next) {
        return new HandlerHopper<>(ObjectSource.from(next), serverHandler);
    }

    @Override
    public MappedHandlerImpl<Object, ? extends ReadableSource<ObjectInputStream>> getHandler() {
        return serverHandler;
    }

    @Override
    public int getPort() {
        return supplier.getPort();
    }
}
