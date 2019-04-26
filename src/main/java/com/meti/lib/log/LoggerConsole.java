package com.meti.lib.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerConsole extends Console {
    private final Logger logger;

    public LoggerConsole() {
        this(LoggerConsole.class.getSimpleName());
    }

    private LoggerConsole(String name) {
        this(Logger.getLogger(name));
    }

    private LoggerConsole(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void accept(Level level, String message) {
        logger.log(level, message);
    }
}
