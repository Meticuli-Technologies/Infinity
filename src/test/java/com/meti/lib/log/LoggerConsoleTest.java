package com.meti.lib.log;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class LoggerConsoleTest {
    @Test
    void constructWithLogger() {
        Logger logger = Mockito.mock(Logger.class);
        LoggerConsole console = new LoggerConsole(logger);
        assertEquals(logger, console.logger);
    }

    @Test
    void constructWithName() {
        LoggerConsole console = new LoggerConsole("test");
        assertEquals("test", console.logger.getName());
    }

    @Test
    void defaultConstruct() {
        LoggerConsole console = new LoggerConsole();
        assertEquals(LoggerConsole.class.getSimpleName(), console.logger.getName());
    }
}