package com.meti.lib;

import com.meti.lib.util.CollectionUtil;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class State extends HashSet<Object> {
    public <T> Optional<T> byClassToSingle(Class<T> tClass) {
        return CollectionUtil.toSingle(byClass(tClass).collect(Collectors.toSet()));
    }

    public <T> Stream<T> byClass(Class<T> tClass) {
        return byPredicate(new TypePredicate<>(tClass))
                .map(new TypeFunction<>(tClass));
    }

    public Stream<Object> byPredicate(Predicate<Object> predicate) {
        return stream()
                .filter(predicate);
    }
}
