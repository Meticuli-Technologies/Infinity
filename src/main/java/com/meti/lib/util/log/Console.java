package com.meti.lib.util.log;

import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public abstract class Console implements BiConsumer<Level, String> {
    private final LogBuilder logBuilder = new LogBuilder();

    public void log(Level level, Exception exception) {
        log(level, null, exception);
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    private void log(Level level, String message, Exception exception){
        String result = logBuilder.log(message, exception);
        accept(level, result);
    }
}
