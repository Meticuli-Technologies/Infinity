package com.meti.lib.util;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> T toSingle(Collection<? extends T> ts) {
        if (ts.size() == 1) {
            return new ArrayList<>(ts).get(0);
        } else {
            throw new IllegalArgumentException("Size of " + ts + " must be 1, otherwise cannot convert to single element");
        }
    }
}
