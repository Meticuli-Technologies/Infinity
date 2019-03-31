package com.meti;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/30/2019
 */
public interface Handler<T> extends Predicate<T>, Function<T, Object> {
}
