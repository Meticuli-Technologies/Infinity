package com.meti.lib.log;

import com.meti.lib.consume.BiCollectionConsumer;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;

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

}
