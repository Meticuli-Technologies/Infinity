package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> Optional<T> toSingle(Collection<T> collection) {
        if (collection.size() == 1) {
            return Optional.ofNullable(new ArrayList<>(collection).get(0));
        } else {
            throw new IllegalArgumentException(collection.size() + " is not equal to 1");
        }
    }
}
