package com.meti.core;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

class InfinityStopper {
    private final InfinitySystem system;

    InfinityStopper(InfinitySystem system) {
        this.system = system;
    }

    void stop(List<Closeable> closeables) {
        close(closeables);
        terminate();
    }

    private void close(List<Closeable> closeables) {
       system.getLogger().log(Level.INFO, "Closing closeables.");
        for (Closeable closeable : closeables) {
            closeImpl(closeable);
        }
    }

    private void closeImpl(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            system.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    private void terminate() {
        try {
            terminateImpl();
        } catch (Exception e) {
            system.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    private void terminateImpl() throws InterruptedException, TimeoutException {
        system.getLogger().log(Level.INFO, "Finalizing futures.");
        logFutures(system.getExecutorServiceManager().finalizeFutures());
        system.getLogger().log(Level.INFO, "Stopping executor service.");
        system.getExecutorServiceManager().stop(Duration.ofSeconds(1));
    }

    private void logFutures(Map<Future<?>, Object> futureMap) {
        futureMap.values()
                .stream()
                .filter(o -> o instanceof Throwable)
                .forEach(o -> system.getLogger().log(Level.WARNING, o.toString()));
    }
}