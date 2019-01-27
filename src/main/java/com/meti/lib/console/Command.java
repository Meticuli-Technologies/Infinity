package com.meti.lib.console;

import java.util.StringTokenizer;
import java.util.stream.StreamSupport;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Command {
    private final String[] args;

    public Command(String line) {
        Iterable<Object> iterable = () -> new StringTokenizer(line).asIterator();
        this.args = StreamSupport.stream(iterable.spliterator(), false)
                .map(Object::toString)
                .toArray(String[]::new);
    }

    public Command(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return String.join(" ", args);
    }
}
