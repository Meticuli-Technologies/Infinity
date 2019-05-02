package com.meti.app.io;

import com.meti.app.io.update.UpdateHandler;
import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;

import java.io.IOException;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    public InfinityServer(ServerSocketSupplier supplier) {
        //TODO: add options for non-shared servers
        super(supplier, true);

        handlers.add(new UpdateHandler());
    }

    @Override
    protected ObjectSource<?> getObjectSource(SocketSource source) throws IOException {
        return new InfinityClient(source);
    }

}
