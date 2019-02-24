package com.meti.lib;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class OptionalUtil {
    private OptionalUtil() {
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> Stream<T> stream(Optional<T> optional) {
        return optional.map(Stream::of)
                .orElseGet(Stream::empty);
    }
}
