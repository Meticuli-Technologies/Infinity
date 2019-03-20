package com.meti.lib.net;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public interface TokenHandler<T, R> extends Predicate<T>, Function<T, R> {
}
