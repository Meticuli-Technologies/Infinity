package com.meti.lib.concurrent;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/1/2019
 */
public class ThreadManager {
    private final ExecutorService service;
    private final Set<Stoppable> stoppables = new HashSet<>();

    public ThreadManager() {
        this(Executors.newCachedThreadPool());
    }

    public ThreadManager(ExecutorService service) {
        this.service = service;
    }

    public Future<?> submit(Runnable task) {
        checkStoppable(task);
        return service.submit(task);
    }

    private void checkStoppable(Object task) {
        if (task instanceof Stoppable) {
            stoppables.add((Stoppable) task);
        }
    }

    public <T> Future<T> submit(Callable<T> callable) {
        checkStoppable(callable);
        return service.submit(callable);
    }

    public void stop() throws InterruptedException {
        stop(Duration.ofSeconds(1));
    }

    private void stop(Duration duration) throws InterruptedException {
        stop(duration, Throwable::printStackTrace);
    }

    public Optional<List<Runnable>> stop(Duration duration, Consumer<Exception> callback) throws InterruptedException {
        for (Stoppable stoppable : stoppables) {
            try {
                stoppable.stop();
            } catch (Exception e) {
                callback.accept(e);
            }
        }

        service.shutdown();

        if (!service.isTerminated()) {
            Thread.sleep(duration.toMillis());
            return Optional.of(service.shutdownNow());
        }

        return Optional.empty();
    }
}
