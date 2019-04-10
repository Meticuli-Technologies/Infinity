package com.meti.app;

import com.meti.lib.log.ConsoleEvent;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class ExitConsumerTest {

    @Test
    void acceptFalse() {
        TestRunnable runnable = new TestRunnable();

        ExitConsumer consumer = new ExitConsumer(runnable);
        consumer.accept(new ConsoleEvent(Level.INFO, "test"));

        assertFalse(runnable.running);
    }

    @Test
    void acceptTrue() {
        TestRunnable runnable = new TestRunnable();

        ExitConsumer consumer = new ExitConsumer(runnable);
        consumer.accept(new ConsoleEvent(Level.SEVERE, "test"));

        assertTrue(runnable.running);
    }

    private class TestRunnable implements Runnable {
        private boolean running;

        @Override
        public void run() {
            running = true;
        }
    }
}