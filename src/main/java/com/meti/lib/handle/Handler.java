package com.meti.lib.handle;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Handler<T> extends Predicate<T>, Consumer<T> {
}
