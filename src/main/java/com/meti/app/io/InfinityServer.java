package com.meti.app.io;

import com.meti.lib.io.MappedServer;
import com.meti.lib.io.SocketSource;
import com.meti.lib.io.SourceSupplier;

import java.io.IOException;
import java.util.function.Consumer;

public class InfinityServer extends MappedServer<SocketSource> {
    public Consumer<SocketSource> onAccept;

    public InfinityServer(SourceSupplier<SocketSource> supplier) {
        super(supplier, true);
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {
        super.accept(source);
        onAccept.accept(source);
    }
}
