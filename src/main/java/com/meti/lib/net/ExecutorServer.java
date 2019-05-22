package com.meti.lib.net;

import com.meti.lib.source.CompoundSource;
import com.meti.lib.source.SourceSupplier;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.meti.lib.concurrent.ExecutorUtil.terminate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public abstract class ExecutorServer<S extends CompoundSource<?, ?>, O extends SourceSupplier<S>> extends Server<S, O> implements Listener {
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
        terminate(service, timeout);
    }

    public abstract Callable<?> buildHopper(S next);

    @Override
    public void listen() {
        service.submit(this);
    }
}
