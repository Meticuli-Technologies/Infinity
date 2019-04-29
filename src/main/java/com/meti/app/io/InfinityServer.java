package com.meti.app.io;

import com.meti.lib.io.MappedServer;
import com.meti.lib.io.SocketSource;
import com.meti.lib.io.SourceSupplier;

import java.io.IOException;

public class InfinityServer extends MappedServer<SocketSource> {
    public InfinityServer(SourceSupplier<SocketSource> supplier) {
        super(supplier, true);
    }

    @Override
    protected void accept(SocketSource source) throws IOException, ClassNotFoundException {

    }
}
