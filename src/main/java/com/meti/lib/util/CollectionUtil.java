package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> Optional<T> toSingle(Collection<T> collection) {
        if (collection.size() > 1) {
            throw new IllegalArgumentException("Size must be 1");
        } else {
            if (collection.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(new ArrayList<>(collection).get(0));
            }
        }
    }
}
