package com.meti.lib.log;

import com.meti.lib.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiConsumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class Console extends Component<ConsoleKey, ConsoleEvent> {
    private final BiConsumer<Level, String> recordConsumer;

    Console(BiConsumer<Level, String> recordConsumer) {

        BiConsumer<Level, String> eventConsumer = new BiConsumer<>() {
            @Override
            public void accept(Level level, String s) {
                //TODO: test this block of code
                eventManager.fire(ConsoleKey.ON_LOG, new ConsoleEvent(level, s));
            }
        };
        if (recordConsumer != null) {
            this.recordConsumer = recordConsumer;
            this.recordConsumer.andThen(eventConsumer);
        }
        else{
            this.recordConsumer = eventConsumer;
        }
    }

    public void log(Level level, Exception exception) {
        log(level, null, exception);
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    public void log(Level level, String message, Exception exception) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append(message);
        }

        if (message != null && exception != null) {
            builder.append("\n");
        }

        if (exception != null) {
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        recordConsumer.accept(level, builder.toString());
    }
}