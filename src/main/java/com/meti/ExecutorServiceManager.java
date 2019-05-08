package com.meti;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/7/2019
 */
public class ExecutorServiceManager {
    private final List<Future<?>> futures = new ArrayList<>();
    private final ExecutorService service;

    public ExecutorServiceManager(ExecutorService service) {
        this.service = service;
    }

    public Map<Future<?>, Object> finalizeFutures() throws ExecutionException, InterruptedException {
        Map<Future<?>, Object> map = new HashMap<>();
        for (Future<?> future : futures) {
            if (future.isDone()) {
                Object token = future.get();
                map.put(future, token);
            } else {
                future.cancel(true);
            }
        }
        return map;
    }

    public void stop(Duration terminationTimeout) throws InterruptedException, TimeoutException {
        if (!service.isShutdown()) {
            service.shutdown();
        }

        if (!service.isTerminated()) {
            boolean timeElapsed = service.awaitTermination(terminationTimeout.toMillis(), TimeUnit.MILLISECONDS);
            if (!timeElapsed) {
                throw new TimeoutException("Termination timeout was exceeded.");
            }
        }
    }

    public ExecutorServiceManager submit(Callable<?> callable) {
        Future<?> future = service.submit(callable);
        futures.add(future);
        return this;
    }
}
