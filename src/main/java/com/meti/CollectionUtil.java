package com.meti;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> T toSingle(Collection<? extends T> ts) {
        if (ts.size() > 1) {
            throw new IllegalArgumentException("Has " + ts.size() + " results, must return 1!");
        } else if (ts.isEmpty()) {
            throw new IllegalArgumentException("No handlers found.");
        } else {
            return new ArrayList<>(ts).get(0);
        }
    }
}
