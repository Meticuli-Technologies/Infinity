package com.meti.lib.log;

import com.meti.lib.consume.BiCollectionConsumer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class ConsoleTest {
    @Test
    void log() {
    /*    new BiCollectionConsumer<Level, String, ArrayList<String>, HashMap<Level, ArrayList<String>>>();*/
    }

    private static class Console {
        private final BiConsumer<Level, String> recordConsumer;

        private Console(BiConsumer<Level, String> recordConsumer) {
            this.recordConsumer = recordConsumer;
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
}
