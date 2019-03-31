package com.meti.lib.collection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class State extends HashSet<Object> {
    public State(Object... initials) {
        addAll(Arrays.asList(initials));
    }

    public <T> Stream<T> search(Class<T> tClass) {
        return search(new TypePredicate<>(tClass))
                .map(new TypeFunction<>(tClass));
    }

    public Stream<Object> search(Predicate<Object> predicate) {
        return stream().filter(predicate);
    }
}
