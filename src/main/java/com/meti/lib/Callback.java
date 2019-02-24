package com.meti.lib;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class Callback implements Consumer<Exception> {
    private final Queue<Exception> exceptions = new LinkedList<>();

    @Override
    public void accept(Exception e) {
        exceptions.add(e);
    }

    public Optional<Exception> getFirst() {
        if (exceptions.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(exceptions.poll());
        }
    }

    public Exception getAll() {
        String message = exceptions.stream()
                .map(e -> {
                    StringWriter writer = new StringWriter();
                    e.printStackTrace(new PrintWriter(writer));
                    return writer.toString();
                })
                .collect(Collectors.joining("\n"));
        return new Exception(message);
    }
}
