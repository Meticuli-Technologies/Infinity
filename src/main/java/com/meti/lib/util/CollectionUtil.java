package com.meti.lib.util;

import java.util.*;

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> T lastStage(List<T> ts) {
        return ts.get(lastStageIndex(ts));
    }

    public static int lastStageIndex(Collection<?> collection) {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("No elements present.");
        }
        return collection.size() - 1;
    }

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
