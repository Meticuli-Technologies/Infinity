package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Set;

public class CollectionUtil {
    public static Object toSingle(Set<Object> set) {
        Object result;
        if (set.size() > 1) {
            result = new IllegalArgumentException("Too many results: " + set.size() + " results were found.");
        } else if (set.isEmpty()) {
            result = new IllegalArgumentException("No results found.");
        } else {
            result = new ArrayList<>(set).get(0);
        }
        return result;
    }
}
