package com.meti.lib.io;

import java.util.concurrent.Callable;

public abstract class Server<S extends Source> {
    private final SourceSupplier<S> supplier;

    public Server(SourceSupplier<S> supplier) {
        this.supplier = supplier;
    }

    protected abstract void accept(S source);

    public ServerListener getListener() {
        return new ServerListener();
    }

    private class ServerListener implements Callable<Server> {
        @Override
        public Server call() {
            while (supplier.isOpen()) {
                accept(supplier.get());
            }
            return Server.this;
        }
    }
}