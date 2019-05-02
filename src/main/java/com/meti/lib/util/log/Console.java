package com.meti.lib.util.log;

import com.meti.lib.util.tryable.TryableFactory;

import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public abstract class Console implements BiConsumer<Level, String> {
    private final LogBuilder logBuilder = new LogBuilder();

    public TryableFactory getFactory() {
        return getFactory(Level.WARNING);
    }

    private TryableFactory getFactory(Level level) {
        return new ConsoleTryableFactory(level);
    }

    public void log(Level level, Exception exception) {
        log(level, null, exception);
    }

    private void log(Level level, String message, Exception exception) {
        String result = logBuilder.log(message, exception);
        accept(level, result);
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    private class ConsoleTryableFactory extends TryableFactory {
        private final Level level;

        ConsoleTryableFactory(Level level) {
            this.level = level;
        }

        @Override
        public void handle(Exception e) {
            log(level, e);
        }
    }
}
