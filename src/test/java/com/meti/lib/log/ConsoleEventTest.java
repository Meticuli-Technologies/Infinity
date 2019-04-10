package com.meti.lib.log;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class ConsoleEventTest {
    @Test
    void construct() {
        ConsoleEvent event = new ConsoleEvent(Level.INFO, "test");
        assertEquals(Level.INFO, event.level);
        assertEquals("test", event.message);
    }
}