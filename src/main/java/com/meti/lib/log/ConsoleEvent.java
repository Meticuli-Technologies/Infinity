package com.meti.lib.log;

import com.meti.lib.collect.event.Event;

import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class ConsoleEvent implements Event {
    public final Level level;
    final String message;

    public ConsoleEvent(Level level, String message) {
        this.level = level;
        this.message = message;
    }
}
