package com.meti.lib.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class State extends ArrayList<Object> {
    public State(Object... initials) {
        addAll(Arrays.asList(initials));
    }

    public <T> Stream<T> byClass(Class<T> tClass) {
        return byPredicate(new TypePredicate<>(tClass))
                .map(new TypeFunction<>(tClass));
    }

    public Stream<Object> byPredicate(Predicate<Object> predicate) {
        return stream().filter(predicate);
    }
}
