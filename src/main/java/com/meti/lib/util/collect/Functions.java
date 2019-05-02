package com.meti.lib.util.collect;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/1/2019
 */
public class Functions {
    private Functions() {
    }

    public static <A, B, C, R> BiFunction<A, C, R> applyFirst(BiFunction<B, C, R> function, Function<A, B> applicator) {
        return (a, c) -> function.apply(applicator.apply(a), c);
    }

/*    public static <A, B, C, R> BiFunction<A, C, R> applySecond(BiFunction<C, B, R> function, Function<A, B> applicator) {
        return (a, c) -> function.apply(a, applicator.apply(c));
    }*/


}
