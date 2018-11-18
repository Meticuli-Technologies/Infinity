package com.meti.lib.collect;

import com.meti.lib.convert.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/15/2018
 */
public class CollectionUtil {
    private CollectionUtil(){}

    @SafeVarargs
    public static <T> ArrayList<T> asArrayList(T... ts){
        return new ArrayList<>(Arrays.asList(ts));
    }

    public static <T> List<T> convert(Collection<?> collection, Converter<T> converter){
        return collection.stream()
                .filter(converter)
                .map(converter)
                .collect(Collectors.toList());
    }
}
