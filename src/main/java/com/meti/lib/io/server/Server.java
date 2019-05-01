package com.meti.lib.io.server;

import com.meti.lib.io.source.Source;
import com.meti.lib.io.source.supplier.SourceSupplier;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class Server<S extends Source, T extends SourceSupplier<S>> {
    public final T supplier;

    Server(T supplier) {
        this.supplier = supplier;
    }

    protected abstract void accept(S source) throws IOException, ClassNotFoundException;

    public ServerListener getListener() {
        return new ServerListener();
    }

    private class ServerListener implements Callable<Server> {
        @Override
        public Server call() throws IOException, ClassNotFoundException {
            while (supplier.isOpen()) {
                accept(supplier.get());
            }
            return Server.this;
        }
    }
}