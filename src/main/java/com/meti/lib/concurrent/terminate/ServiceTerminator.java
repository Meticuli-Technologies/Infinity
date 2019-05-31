package com.meti.lib.concurrent.terminate;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ServiceTerminator implements Terminator {
    private final ExecutorService service;

    public ServiceTerminator(ExecutorService service) {
        this.service = service;
    }

    @Override
    public void terminate(Duration duration) throws InterruptedException {
        List<Runnable> runnables = service.shutdownNow();
        this.service.awaitTermination(duration.toMillis(), TimeUnit.MILLISECONDS);
        if (!runnables.isEmpty()) {
            throwIfNonEmpty(runnables);
        }
    }

    private void throwIfNonEmpty(Iterable<? extends Runnable> runnables) {
        StringBuilder builder = new StringBuilder("The following runnables were awaiting termination:");
        for (Runnable runnable : runnables) {
            builder.append("\n\t").append(runnable);
        }
        throw new IllegalStateException(builder.toString());
    }
}