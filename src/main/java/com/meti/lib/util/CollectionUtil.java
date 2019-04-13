package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> Optional<T> toSingle(Collection<? extends T> ts) {
        if (ts.size() > 1) {
            throw new IllegalArgumentException("Has " + ts.size() + " results, must return 1!");
        } else if (ts.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(new ArrayList<>(ts).get(0));
        }
    }
}
