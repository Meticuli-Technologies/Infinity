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
class Console {
    private final BiConsumer<Level, String> recordConsumer;

    Console(BiConsumer<Level, String> recordConsumer) {
        this.recordConsumer = recordConsumer;
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

    /*private static class LoggerConsole extends Console {
        public final Logger logger;

        private LoggerConsole() {
            this(LoggerConsole.class.getSimpleName());
        }

        private LoggerConsole(String name) {
            this(Logger.getLogger(name));
        }

        private LoggerConsole(Logger logger) {
            super(logger::log);
            this.logger = logger;
        }
    }*/
}
