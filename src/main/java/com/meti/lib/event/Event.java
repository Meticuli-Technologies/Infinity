package com.meti.lib.event;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Event<T> extends Predicate<T>, Consumer<T> {
}
