package com.meti.lib;

import java.util.Collection;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/22/2019
 */
public class CollectionUtil {
    private CollectionUtil() {
    }

    public static boolean isValuePresent(Collection<?> collection) {
        return !collection.isEmpty();
    }
}
