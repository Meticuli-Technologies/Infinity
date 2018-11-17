package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    public static <T> List<T> convert(Collection<?> collection){
        return null;
    }
}
