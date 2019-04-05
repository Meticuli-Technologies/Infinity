package com.meti.lib;

import com.meti.lib.type.TypeFunction;
import com.meti.lib.type.TypePredicate;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
public class State extends ArrayList<Object> {
    public <T> Stream<T> byClass(Class<T> tClass) {
        return stream()
                .filter(new TypePredicate<>(tClass))
                .map(new TypeFunction<>(tClass));
    }

    public Stream<Object> byPredicate(Predicate<Object> predicate) {
        return stream().filter(predicate);
    }
}
