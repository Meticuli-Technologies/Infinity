package com.meti.lib;

import java.util.ArrayList;
import java.util.Collection;

public final class CollectionUtil {
    private CollectionUtil() {
    }

    public static <T> T toSingle(Collection<T> toSingle) {
        return new ArrayList<>(toSingle).get(0);
    }
}
