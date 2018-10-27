package com.meti.lib;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public class CollectionsUtil {
    private CollectionsUtil(){}

    public static <T> Set<T> toSet(T... parameters) {
        return Stream.of(parameters).collect(Collectors.toSet());
    }

    public static <T>List<T> toList(T... parameters){
        return Stream.of(parameters).collect(Collectors.toList());
    }
}
