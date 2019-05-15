package com.meti.core;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

class InfinityStopper {
    private final Infinity infinity;

    public InfinityStopper(Infinity infinity) {
        this.infinity = infinity;
    }

    void stop() {
        close();
        terminate();
    }

    private void close() {
        infinity.getLogger().log(Level.INFO, "Closing closeables.");
        for (Closeable closeable : infinity.getCloseables()) {
            closeImpl(closeable);
        }
    }

    private void terminate() {
        try {
            terminateImpl();
        } catch (Exception e) {
            infinity.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    private void closeImpl(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            infinity.getLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    private void terminateImpl() throws InterruptedException, TimeoutException {
        infinity.getLogger().log(Level.INFO, "Finalizing futures.");
        logFutures(infinity.getExecutorServiceManager().finalizeFutures());
        infinity.getLogger().log(Level.INFO, "Stopping executor service.");
        infinity.getExecutorServiceManager().stop(Duration.ofSeconds(1));
    }

    private void logFutures(Map<Future<?>, Object> futureMap) {
        futureMap.values()
                .stream()
                .filter(o -> o instanceof Throwable)
                .forEach(o -> infinity.getLogger().log(Level.WARNING, o.toString()));
    }
}