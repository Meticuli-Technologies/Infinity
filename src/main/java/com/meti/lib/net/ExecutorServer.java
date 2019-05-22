package com.meti.lib.net;

import com.meti.lib.handle.Hopper;
import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public abstract class ExecutorServer<S extends CompoundSource<ObjectInputStream, ?>, O extends SourceSupplier<?, ?, S>, H extends Hopper<S>> extends Server<S, O> {
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(4L);
    private final ExecutorService service;
    private final Duration timeout;

    public ExecutorServer(O supplier) {
        this(supplier, Executors.newCachedThreadPool(), DEFAULT_TIMEOUT);
    }

    public ExecutorServer(O supplier, ExecutorService service, Duration timeout) {
        super(supplier);
        this.service = service;
        this.timeout = timeout;
    }

    @Override
    protected void handle(S next) {
        service.submit(buildHopper(next));
    }

    @Override
    public void close() throws IOException {
        super.close();
        service.shutdownNow();
        if (!service.isTerminated()) {
            try {
                service.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException("Could not terminate service", e);
            }
        }
    }

    public abstract H buildHopper(S next);

    public Future<O> listen() {
        return service.submit(this);
    }
}
