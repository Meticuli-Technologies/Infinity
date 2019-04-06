package com.meti.lib.log;

import com.meti.lib.consume.BiCollectionConsumer;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class ConsoleTest {
    @Test
    void ON_LOG_event(){
    /*    Console console = new Console(null);
        console.eventManager.compound(ConsoleKey.ON_LOG, new Consumer<ConsoleEvent>() {
            @Override
            public void accept(ConsoleEvent consoleEvent) {

            }
        });*/
    }

    @Test
    void logWithoutException() {
        TestConsole testConsole = new TestConsole();
        testConsole.log(Level.INFO, "test");
        assertEquals(Level.INFO, testConsole.level);
        assertEquals("test", testConsole.message);
    }

    @Test
    void logWithoutMessage(){
        TestConsole testConsole = new TestConsole();
        Exception exception = new Exception();
        testConsole.log(Level.INFO, exception);
        assertEquals(testConsole.level, Level.INFO);
        assertEquals(testConsole.exception, exception);
    }

    @Test
    void log() {
        BiCollectionConsumer<Level, String, ArrayList<String>, HashMap<Level, ArrayList<String>>> consumer = new BiCollectionConsumer<>(ArrayList::new, new HashMap<>());
        Console console = new Console(consumer);

        String message = "test";
        Exception exception = new Exception();
        console.log(Level.INFO, message, exception);

        Optional<ArrayList<String>> optional = consumer.getCollection(Level.INFO);
        assertTrue(optional.isPresent());

        ArrayList<String> list = optional.get();
        assertEquals(1, list.size());

        StringWriter writer = new StringWriter();
        exception.printStackTrace(new PrintWriter(writer));
        assertTrue(list.contains("test\n" + writer.toString()));
    }

    private class TestConsole extends Console {
        private Level level;
        private String message;
        private Exception exception;

        TestConsole() {
            super(null);
        }

        @Override
        public void log(Level level, String message, Exception exception) {
            this.level = level;
            this.message = message;
            this.exception = exception;
        }
    }
}
