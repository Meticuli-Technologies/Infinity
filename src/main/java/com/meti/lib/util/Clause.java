package com.meti.lib.util;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/28/2019
 */
@FunctionalInterface
public interface Clause<P, R> {
    R applyThrows(P p) throws Throwable;

    static <P, R> Function<P, Optional<R>> wrap(Clause<P, R> clause){
        return p -> {
            try {
                return Optional.of(clause.applyThrows(p));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return Optional.empty();
            }
        };
    }
}
