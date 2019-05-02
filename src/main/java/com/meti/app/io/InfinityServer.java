package com.meti.app.io;

import com.meti.app.core.runtime.InfinityState;
import com.meti.app.io.update.server.UpdateManager;
import com.meti.app.io.update.server.UpdateTokenHandler;
import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.server.handle.TokenHandler;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.Source;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;
import com.meti.lib.util.Streams;
import com.meti.lib.util.collect.TypeFunction;
import com.meti.lib.util.collect.TypePredicate;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    private final UpdateManager<Source> updateManager = new UpdateManager<>();

    public InfinityServer(ServerSocketSupplier supplier, InfinityState state) {
        //TODO: update options for non-shared servers
        super(supplier, true);

        tokenHandlers.add(new UpdateTokenHandler(updateManager));
        Streams.instanceStream(state.getConsole().getFactory(), state.getModuleManager().getImplementations(ServerHandler.class))
                .filter(new TypePredicate<>(ServerHandler.class))
                .map(new TypeFunction<>(ServerHandler.class))
                .map(ServerHandler::getHandlers)
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
