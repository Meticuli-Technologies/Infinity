package com.meti.app.io;

import com.meti.app.io.update.server.UpdateTokenHandler;
import com.meti.app.io.update.server.UpdateManager;
import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;

import java.io.IOException;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    private final UpdateManager<SocketSource> updateManager = new UpdateManager<>();

    public InfinityServer(ServerSocketSupplier supplier) {
        //TODO: update options for non-shared servers
        super(supplier, true);

        tokenHandlers.add(new UpdateTokenHandler(updateManager));
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {
        super.accept(updateManager.process(source));
    }

    @Override
    protected ObjectSource<?> getObjectSource(SocketSource source) throws IOException {
        return new InfinityClient(source);
    }
}
