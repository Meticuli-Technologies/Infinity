package com.meti.lib.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class Console {
    private final BiConsumer<Level, String> recordConsumer;

    Console(BiConsumer<Level, String> recordConsumer) {
        this.recordConsumer = recordConsumer;
    }


    public void log(Level level, Exception exception) {
        log(level, null, exception);
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    public void log(Level level, String message, Exception exception) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append(message);
        }

        if (message != null && exception != null) {
            builder.append("\n");
        }

        if (exception != null) {
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        recordConsumer.accept(level, builder.toString());
    }

}
