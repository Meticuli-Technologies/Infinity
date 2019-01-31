package com.meti.lib.console;

import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Command {
    public final List<String> args;

    public Command(String line) {
        //separates the line by a tokenizer and converts the elements to an array
        Iterable<Object> iterable = () -> new StringTokenizer(line).asIterator();
        this.args = StreamSupport.stream(iterable.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    public Command(List<String> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return String.join(" ", args);
    }
}
