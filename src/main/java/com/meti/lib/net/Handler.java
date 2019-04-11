package com.meti.lib.net;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public interface Handler<T> extends Predicate<Object>, Function<Object, T> {
}
