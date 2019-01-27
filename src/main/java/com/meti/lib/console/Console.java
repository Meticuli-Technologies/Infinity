package com.meti.lib.console;

import com.meti.lib.bucket.Bucket;
import com.meti.lib.bucket.BucketManager;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Console {
    private final BucketManager<Command> manager = new BucketManager<>();

    public void addHandler(Predicate<Command> predicate, Consumer<Command> consumer) {
        manager.add(new Bucket<>(predicate, consumer));
    }

    public void run(Command command) {
        Set<Bucket<Command>> result = manager.handle(command);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Invalid command: " + command);
        }
    }
}
