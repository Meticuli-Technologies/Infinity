package com.meti.lib.util;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

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

    public static Object computeFromResults(List<Object> results) {
        if (results.size() > 1) {
            return new IllegalArgumentException("Too many results.");
        }
        return computeSingularResult(results);
    }

    private static Object computeSingularResult(List<Object> results) {
        return results.isEmpty() ?
                new IllegalArgumentException("No results found.") :
                results.get(0);
    }
}
