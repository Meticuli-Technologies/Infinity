package com.meti.lib;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/12/2019
 */
public interface Handler<T> extends Predicate<T>, Consumer<T> {
}
