package com.meti.app.io;

import com.meti.app.io.update.server.UpdateManager;
import com.meti.app.io.update.server.UpdateTokenHandler;
import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;
import com.meti.lib.module.ModuleManager;
import com.meti.lib.util.Streams;
import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;
import com.meti.lib.util.tryable.TryableFactory;

import java.io.IOException;
import java.util.Collection;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    private final UpdateManager updateManager = new UpdateManager();

    public InfinityServer(ServerSocketSupplier supplier, TryableFactory factory, ModuleManager moduleManager) {
        //TODO: update options for non-shared servers
        super(supplier, true);

        tokenHandlers.add(new UpdateTokenHandler(updateManager));
        Streams.instanceStream(factory, moduleManager.getImplementations(ServerHandler.class))
                .filter(new TypePredicate<>(ServerHandler.class))
                .map(new TypeFunction<>(ServerHandler.class))
                .map((ServerHandler serverHandler) -> serverHandler.getHandlers(updateManager))
                .flatMap(Collection::stream)
                .forEach(tokenHandlers::add);
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {
        updateManager.process(source);
        super.accept(source);
    }

    @Override
    protected ObjectSource<?> getObjectSource(SocketSource source) throws IOException {
        return new InfinityClient(source);
    }
}
