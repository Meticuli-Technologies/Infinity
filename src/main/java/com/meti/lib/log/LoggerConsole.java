package com.meti.lib.log;

import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class LoggerConsole extends Console {
    public final Logger logger;

    public LoggerConsole() {
        this(LoggerConsole.class.getSimpleName());
    }

    public LoggerConsole(String name) {
        this(Logger.getLogger(name));
    }

    public LoggerConsole(Logger logger) {
        super(logger::log);
        this.logger = logger;
    }
}
