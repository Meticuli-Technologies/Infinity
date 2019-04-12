package com.meti.lib.log;

import java.util.logging.Level;

public class ConsoleRecord {
    private final Level level;
    private final String message;

    public ConsoleRecord(Level level, String message) {
        this.level = level;
        this.message = message;
    }
}
