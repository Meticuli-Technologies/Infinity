package com.meti.lib;

import java.util.function.BiConsumer;
import java.util.logging.Level;

import static com.meti.lib.util.ExceptionUtil.stackTraceString;

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
        appendMessage(message, builder)
                .appendNewLine(message, exception, builder)
                .appendException(exception, builder);
        return logBuilder(level, builder);
    }

    private String logBuilder(Level level, StringBuilder builder) {
        String result = builder.toString();
        handler.accept(level, result);
        return result;
    }

    private void appendException(Exception exception, StringBuilder builder) {
        if (exception != null) {
            builder.append(stackTraceString(exception));
        }
    }

    private Console appendNewLine(String message, Exception exception, StringBuilder builder) {
        if (message != null && exception != null) {
            builder.append("\n");
        }
        return this;
    }

    private Console appendMessage(String message, StringBuilder builder) {
        if (message != null) {
            builder.append(message);
        }
        return this;
    }

    public String log(Level level, String message) {
        return log(level, message, null);
    }
}
