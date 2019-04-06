package com.meti.lib.consume;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.Duration;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
class CompletableConsumerTest {
    @Test
    void get(){
        CompletableConsumer<String> consumer = new CompletableConsumer<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Void> future = executor.submit(() -> {
            assertEquals("test", consumer.get());
            return null;
        });

        consumer.accept("test");
        assertTimeoutPreemptively(Duration.ofSeconds(1), (ThrowingSupplier<Void>) future::get);
        assertTrue(executor.shutdownNow().isEmpty());
    }

    private class CompletableConsumer<T> extends CompletableFuture<T> implements Consumer<T> {
        @Override
        public void accept(T t) {
            complete(t);
        }
    }
}
