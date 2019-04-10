package com.meti.lib.util;

import java.util.Optional;

@SuppressWarnings("ALL")
public class OptionalUtil {
    private OptionalUtil() {
    }

    public static void throwIfPresent(Optional<Exception> optional) {
        if (optional.isPresent()) {
            try {
                throw optional.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
