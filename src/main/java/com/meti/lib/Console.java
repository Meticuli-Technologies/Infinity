package com.meti.lib;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class Console {
    private final BiConsumer<Level, String> handler;

    public Console(BiConsumer<Level, String> handler) {
        this.handler = handler;
    }

    public String log(Level level, Exception exception) {
        return log(level, null, exception);
    }

    private String log(Level level, String message, Exception exception) {
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

        String result = builder.toString();
        handler.accept(level, result);
        return result;
    }

    public String log(Level level, String message) {
        return log(level, message, null);
    }
}
