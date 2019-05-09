package com.meti;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class Server implements Callable<Server>, Closeable {
    private final ExecutorServiceManager manager;
    private final SourceSupplier sourceSupplier;
    private final TokenHandler handler;

    public Server(SourceSupplier sourceSupplier, ExecutorServiceManager manager, TokenHandler handler) throws IOException {
        this.sourceSupplier = sourceSupplier;
        this.manager = manager;
        this.handler = handler;
    }

    @Override
    public Server call() throws Exception {
        while (sourceSupplier.isOpen()) {
            Source accepted = sourceSupplier.accept();
            manager.submit(new ServerHandler(handler, accepted));
        }

        return this;
    }

    @Override
    public void close() throws IOException {
        sourceSupplier.close();
    }

    public Server listen() {
        manager.submit(this);
        return this;
    }
}