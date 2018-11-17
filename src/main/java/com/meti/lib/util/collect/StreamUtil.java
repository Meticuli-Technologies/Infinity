package com.meti.lib.util.collect;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/16/2018
 */
public class StreamUtil {
    private StreamUtil(){}

    public static <T> Set<T> asSet(T... objs){
        return Stream.of(objs).collect(Collectors.toSet());
    }

    public static <T>List<T> asList(T... objs){
        return Stream.of(objs).collect(Collectors.toList());
    }
}
