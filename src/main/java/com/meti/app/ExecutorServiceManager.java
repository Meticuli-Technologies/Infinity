package com.meti.app;

import com.meti.lib.collect.Lister;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorServiceManager {
    private final Lister lister = new Lister();
    private final Duration terminationDuration;
    public final ExecutorService service;

    public ExecutorServiceManager(ExecutorService service, Duration terminationDuration) {
        this.service = service;
        this.terminationDuration = terminationDuration;
    }

    public void checkTerminated() throws Exception {
        if (!service.isTerminated()) {
            terminate();
        }
    }

    private void terminate() throws InterruptedException, TimeoutException {
        if (!terminationSuccessful()) {
            throw new TimeoutException("Service failed to terminate within " + terminationDuration.toString());
        }
    }

    private boolean terminationSuccessful() throws InterruptedException {
        return !service.awaitTermination(terminationDuration.toMillis(), TimeUnit.MILLISECONDS);
    }

    public Optional<String> getTaskString() {
        if (service.isShutdown()) {
            throw new IllegalStateException("Service should still be running!");
        }

        return lister.listFrom(service.shutdownNow());
    }
}