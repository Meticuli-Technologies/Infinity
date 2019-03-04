package com.meti.lib.util;

import java.util.function.Supplier;

public class NoStateSupplier implements Supplier<IllegalStateException> {
    @Override
    public IllegalStateException get() {
        throw new IllegalStateException("State not set");
    }
}
