package com.meti.lib.log;

import com.meti.lib.event.Event;

import java.util.logging.Level;

public class ConsoleEvent extends Event {
    public static final int ON_LOG = 0;

    public ConsoleEvent(Level level, String message) {
        super(new Object[]{level, message});
    }

    public Level getLevel() {
        return (Level) args[0];
    }

    public String getMessage() {
        return (String) args[1];
    }
}
