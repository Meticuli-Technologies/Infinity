package com.meti.lib.console;

import com.meti.lib.bucket.Bucket;
import com.meti.lib.bucket.BucketManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Console {
    private final BucketManager<Command> manager = new BucketManager<>();
    private final Logger logger;

    public Console() {
        this(Console.class.getSimpleName());
    }

    public Console(String name) {
        this.logger = Logger.getLogger(name);
    }

    public void log(Level level, Exception exception) {
        log(level, null, exception);
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

        logger.log(level, builder.toString());
    }

    public void log(Level level, String message) {
        log(level, message, null);
    }

    public void addHandler(Predicate<Command> predicate, Consumer<Command> consumer) {
        manager.add(new Bucket<>(predicate, consumer));
    }

    public Set<Consumer<Command>> run(Command command) {
        Set<Bucket<Command>> result = manager.handle(command);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }

        return result.stream()
                .map(commandBucket -> commandBucket.handler)
                .collect(Collectors.toSet());
    }
}
