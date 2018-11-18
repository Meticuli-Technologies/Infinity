package com.meti.lib.collect;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/16/2018
 */
class StreamUtil {
    private StreamUtil(){}

    @SafeVarargs
    public static <T> Set<T> asSet(T... objects){
        return Stream.of(objects).collect(Collectors.toSet());
    }

    @SafeVarargs
    public static <T>List<T> asList(T... objects){
        return Stream.of(objects).collect(Collectors.toList());
    }
}
