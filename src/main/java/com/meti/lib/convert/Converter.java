package com.meti.lib.convert;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 12/19/2018
 */
public abstract class Converter<I, O> implements Predicate<I>, Function<I, O> {
    public O convert(I input) {
        if (canConvert(input)) {
            return apply(input);
        } else {
            throw new IllegalArgumentException("Cannot parse input " + input);
        }
    }

    public boolean canConvert(I input) {
        return test(input);
    }
}
