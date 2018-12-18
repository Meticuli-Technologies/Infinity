package com.meti.lib.convert;

import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/17/2018
 */
public interface Converter<T> extends Predicate<Object>, Function<Object, T> {

    static <T> T applyConverter(Object applicant, Converter<T> converter) {
        return converter.apply(applicant);
    }
}
