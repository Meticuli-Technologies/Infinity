package com.meti.lib.log;

import com.meti.lib.event.Component;
import com.meti.lib.trys.Catcher;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.meti.lib.log.ConsoleEvent.ON_LOG;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class Console extends Component<ConsoleEvent> {
    private final Logger logger;

    public Console() {
        this(Console.class.getSimpleName());
    }

    public Console(String name) {
        this(Logger.getLogger(name));
    }

    public Console(Logger logger) {
        this.logger = logger;
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    public void log(Level level, String message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        if (message != null) {
            builder.append(message);
        }

        if (message != null && throwable != null) {
            builder.append("\n");
        }

        if (throwable != null) {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            builder.append(writer.toString());
        }

        eventManager.fireEvent(ON_LOG, new ConsoleEvent(level, message));
        logger.log(level, builder.toString());
    }

    public Catcher ofSevere() {
        return ofLevel(Level.SEVERE);
    }

    public Catcher ofLevel(Level level) {
        return e -> log(level, e);
    }

    public void log(Level level, Throwable throwable) {
        log(level, null, throwable);
    }

    public Catcher ofWarning() {
        return ofLevel(Level.WARNING);
    }

    public <T> BiFunction<T, Throwable, T> biFunction() {
        return biFunction(Level.WARNING);
    }

    public <T> BiFunction<T, Throwable, T> biFunction(Level level) {
        return (t, throwable) -> {
            if (throwable != null) {
                log(Level.WARNING, throwable);
                return null;
            } else {
                return t;
            }
        };
    }
}
