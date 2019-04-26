package com.meti.lib.log;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public abstract class Console implements BiConsumer<Level, String> {
    private final LogBuilder logBuilder = new LogBuilder();

    public <T> Optional<T> logAsEmpty(Level level, Exception e) {
        log(level, e);
        return Optional.empty();
    }

    public String log(Level level, Exception exception) {
        return log(level, null, exception);
    }

    public String log(Level level, String message) {
        return log(level, message, null);
    }

    public String log(Level level, String message, Exception exception){
        String result = logBuilder.log(message, exception);
        accept(level, result);
        return result;
    }
}
