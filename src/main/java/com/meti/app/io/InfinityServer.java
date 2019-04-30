package com.meti.app.io;

import com.meti.lib.io.server.MappedServer;
import com.meti.lib.io.source.ObjectSource;
import com.meti.lib.io.source.SocketSource;
import com.meti.lib.io.source.supplier.ServerSocketSupplier;

import java.io.IOException;
import java.util.function.Consumer;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    private Consumer<SocketSource> onAccept;

    public InfinityServer(ServerSocketSupplier supplier) {
        super(supplier, true);
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {
        super.accept(source);
        onAccept.accept(source);
    }

    @Override
    protected ObjectSource<?> getObjectSource(SocketSource source) throws IOException {
        return new InfinityClient(source);
    }

    public InfinityServer setOnAccept(Consumer<SocketSource> onAccept) {
        this.onAccept = onAccept;
        return this;
    }
}
