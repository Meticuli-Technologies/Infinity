package com.meti.lib.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static boolean containsIndex(Collection<?> collection, int index){
        return collection.size() > index;
    }

    public static <T> Optional<T> lastElement(List<T> ts) {
        if (lastIndex(ts).isPresent()) {
            return Optional.of(ts.get(lastIndex(ts).getAsInt()));
        } else {
            return Optional.empty();
        }
    }

    private static OptionalInt lastIndex(Collection<?> collection) {
        if (collection.isEmpty()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(collection.size() - 1);
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
