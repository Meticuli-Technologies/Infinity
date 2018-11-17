package com.meti.lib.util;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public interface Converter<T> extends Predicate<Object>, Function<Object, T> {
}
