package com.meti.lib.log;

import org.junit.jupiter.api.Test;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class ConsoleTest {
    @Test
    void log() {
        Console console = new Console.LoggerConsole();
    }

    private static class Console {
        private static class LoggerConsole extends Console {
        }
    }
}
