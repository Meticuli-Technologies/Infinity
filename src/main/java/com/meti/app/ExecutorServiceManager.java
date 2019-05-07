package com.meti.app;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceManager {
    private final List<Future<?>> futures = new ArrayList<>();
    private final ExecutorService service;

    public ExecutorServiceManager(ExecutorService service) {
        this.service = service;
    }

    public ExecutorServiceManager submit(Callable<?> callable) {
        futures.add(service.submit(callable));
        return this;
    }

    public void terminate(Duration timeout) throws Exception {
        futures.forEach(future -> future.cancel(true));

        if (!service.isShutdown()) service.shutdown();
        if (!service.isTerminated()) {
            boolean result = service.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
            if (!result) {
                throw new TimeoutException("Termination sequence timed out.");
            }
        }
    }
}