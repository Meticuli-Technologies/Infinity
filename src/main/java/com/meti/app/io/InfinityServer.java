package com.meti.app.io;

import com.meti.app.io.update.UpdateBundle;
import com.meti.app.io.update.UpdateRequest;
import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.server.handle.TypeHandler;
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

    private static class UpdateHandler extends TypeHandler<UpdateRequest, SocketSource, UpdateBundle> {
        public UpdateHandler() {
            super(UpdateRequest.class);
        }

        @Override
        protected UpdateBundle handle(UpdateRequest updateRequest, SocketSource source) {
            return null;
        }
    }
}
