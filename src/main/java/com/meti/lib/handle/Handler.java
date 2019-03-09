package com.meti.lib.handle;

import com.meti.lib.State;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Handler<T> extends Predicate<T>, Consumer<T> {
    void setState(State state);
}
