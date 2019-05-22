package com.meti.lib.concurrent;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public class ExecutorUtil {
    private ExecutorUtil(){}

    public static void terminate(ExecutorService service, Duration timeout) throws IOException {
        service.shutdownNow();
        if (!service.isTerminated()) {
            try {
                service.awaitTermination(timeout.toMillis(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException("Could not terminate service", e);
            }
        }
    }
}
