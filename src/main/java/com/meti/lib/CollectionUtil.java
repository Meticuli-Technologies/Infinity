package com.meti.lib;

import java.util.ArrayList;
import java.util.Collection;

public final class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> T toSingle(Collection<T> toSingle) {
        if (toSingle.isEmpty()) {
            throw new IllegalArgumentException("No elements found in " + toSingle);
        }
        if (toSingle.size() > 1) {
            throw new IllegalArgumentException("Too many elements found in " + toSingle);
        }
        return new ArrayList<>(toSingle).get(0);
    }
}
