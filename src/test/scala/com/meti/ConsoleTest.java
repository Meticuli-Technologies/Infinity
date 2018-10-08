package com.meti;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/8/2018
 */
class ConsoleTest {
    private Console console = new Console();

    @Test
    void log() {
        String msg = "test";
        Exception error = new Exception();

        String result = console.log(Level.INFO, msg, error);

        StringBuilder builder = new StringBuilder();
        builder.append(msg);
        builder.append("\n");

        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        builder.append(writer.toString());

        assertEquals(builder.toString(), result);
    }

    @Test
    void log1() {
        String msg = "test";

        assertEquals(msg, console.log(Level.INFO, msg));
    }

    @Test
    void log2() {
        Exception e = new Exception();
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));

        assertEquals(writer.toString(), console.log(Level.INFO, e));
    }

    @Test
    void getExceptionSupplier() {
        TestConsole console = new TestConsole();
        Supplier<Consumer<Exception>> supplier = console.getExceptionSupplier(Level.INFO);
        assertNotNull(supplier);

        Consumer<Exception> consumer = supplier.get();
        assertNotNull(consumer);

        Exception t = new Exception();
        consumer.accept(t);

        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));

        assertEquals(writer.toString(), console.resultsMap.get(Level.INFO).get(0));
    }

    private class TestConsole extends Console {
        public final Map<Level, List<String>> resultsMap = new HashMap<>();

        @Override
        public String log(Level level, String msg, Exception e) {
            String result = super.log(level, msg, e);
            List<String> list;
            if (resultsMap.containsKey(level)) {
                list = resultsMap.get(level);
            } else {
                list = new ArrayList<>();
                resultsMap.put(level, list);
            }
            list.add(result);
            return result;
        }
    }
}