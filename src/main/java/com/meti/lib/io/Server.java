package com.meti.lib.io;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class Server<S extends Source> {
    private final SourceSupplier<S> supplier;

    public Server(SourceSupplier<S> supplier) {
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