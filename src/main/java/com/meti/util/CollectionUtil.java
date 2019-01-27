package com.meti.util;

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
        return new ArrayList<>(ts).get(0);
    }
}
