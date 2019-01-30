package com.meti.lib.bucket;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class Bucket<T> {
    private final Predicate<T> filter;
    private final Consumer<T> handler;

    public Bucket(Predicate<T> filter, Consumer<T> handler) {
        this.filter = filter;
        this.handler = handler;
    }

    public boolean containsAllParameters(Object... objects) {
        return Arrays.stream(objects).allMatch(this::containsParameter);
    }

    public boolean containsParameter(Object obj) {
        if (filter instanceof Parameterized<?>) {
            Object[] parameters = ((Parameterized) filter).getParameters();
            return IntStream.range(0, parameters.length).anyMatch(i -> parameters[i].equals(obj));
        }
        return false;
    }

    public Optional<Contentable<?, ?>> getContent() {
        if (handler instanceof Contentable) {

            //wildcards are required here because of the return type
            return Optional.of((Contentable<?, ?>) handler);
        } else {
            return Optional.empty();
        }
    }

    public boolean test(T t) {
        return filter.test(t);
    }

    public void process(T t) {
        if (!filter.test(t)) {
            throw new IllegalArgumentException("Cannot process " + t + " because it fails to qualify for " + filter);
        }

        handler.accept(t);
    }
}
