package com.meti.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public class CollectionUtil {
    private CollectionUtil(){}

    public static <T> Optional<T> toSingle(Collection<T> collection) {
        if (collection.isEmpty()) {
            return Optional.empty();
        }

        if (collection.size() > 1) {
            throw new IllegalArgumentException("Size must be equal to 1.");
        }

        return Optional.of(new ArrayList<>(collection).get(0));
    }
}
