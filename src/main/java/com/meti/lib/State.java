package com.meti.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class State extends ArrayList<Object> {
    public <T> List<T> byClass(Class<T> tClass) {
        return stream()
                .filter(o -> tClass.isAssignableFrom(o.getClass()))
                .map((Function<Object, Optional<T>>) o -> {
                    try {
                        return Optional.of(tClass.cast(o));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Optional.empty();
                    }
                })
                .flatMap(new Function<Optional<T>, Stream<T>>() {
                    @Override
                    public Stream<T> apply(Optional<T> optionalT) {
                        return optionalT.map(Stream::of)
                                .orElseGet(Stream::empty);
                    }
                })
                .collect(Collectors.toList());
    }
}
