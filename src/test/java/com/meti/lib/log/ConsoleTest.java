package com.meti.lib.log;

import com.meti.lib.consume.BiCollectionConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class ConsoleTest {
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

    private static class Console {
        private final BiConsumer<Level, String> recordConsumer;

        private Console(BiConsumer<Level, String> recordConsumer) {
            this.recordConsumer = recordConsumer;
        }

        public void log(Level level, String message, Exception exception) {
            StringBuilder builder = new StringBuilder();
            if(message != null){
                builder.append(message);
            }

            if(message != null && exception != null){
                builder.append("\n");
            }

            if(exception != null){
                StringWriter writer = new StringWriter();
                exception.printStackTrace(new PrintWriter(writer));
                builder.append(writer.toString());
            }

            recordConsumer.accept(level, builder.toString());
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
