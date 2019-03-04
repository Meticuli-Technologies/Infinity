package com.meti.lib.net.token;

import com.meti.lib.State;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface TokenHandler<T> extends Consumer<T>, Predicate<T> {
    void setState(State state);
}