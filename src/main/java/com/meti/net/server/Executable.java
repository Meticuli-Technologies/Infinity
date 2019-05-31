package com.meti.net.server;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/31/2019
 */
public abstract class Executable implements Callable<Void>, Closeable {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(1);
    private final ExecutorService internalService = Executors.newCachedThreadPool();
    private final Terminator serviceTerminator;
    private final Duration timeout;

    protected Executable() {
        this(DEFAULT_TIMEOUT);
    }

    protected Executable(Duration timeout) {
        this.timeout = timeout;
        this.serviceTerminator = new ServiceTerminator(internalService);
    }

    @Override
    public void close() throws IOException {
        try {
            serviceTerminator.terminate(timeout);
        } catch (InterruptedException e) {
            throw new IOException("Failed to terminate service.", e);
        }
    }

    protected ExecutorService getInternalService() {
        return internalService;
    }

    public void listen() {
        internalService.submit(this);
    }
}
