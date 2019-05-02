package com.meti.lib.io.server.handle;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Handler<T, R> extends Predicate<T>, Function<T, R> {
}
