package com.meti.lib.io.server.handle;

import com.meti.lib.io.source.Source;

import java.util.function.BiFunction;
import java.util.function.Predicate;

public interface TokenHandler<T, R> extends Predicate<T>, BiFunction<T, Source, R> {
}
