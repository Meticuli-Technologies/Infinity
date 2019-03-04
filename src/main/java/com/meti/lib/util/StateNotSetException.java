package com.meti.lib.util;

import java.util.function.Supplier;

public class StateNotSetException implements Supplier<IllegalStateException> {
    @Override
    public IllegalStateException get() {
        throw new IllegalStateException("State not set");
    }
}
