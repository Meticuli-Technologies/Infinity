package com.meti.app.io;

import com.meti.lib.io.MappedServer;
import com.meti.lib.io.ServerSocketSupplier;
import com.meti.lib.io.SocketSource;

import java.io.IOException;
import java.util.function.Consumer;

public class InfinityServer extends MappedServer<SocketSource, ServerSocketSupplier> {
    public Consumer<SocketSource> onAccept;

    public InfinityServer(ServerSocketSupplier supplier) {
        super(supplier, true);
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {
        super.accept(source);
        onAccept.accept(source);
    }
}
