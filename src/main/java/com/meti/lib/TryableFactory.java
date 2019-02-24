package com.meti.lib;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class TryableFactory<C extends Consumer<Exception>> {
    private final C callback;

    public TryableFactory(C callback) {
        this.callback = callback;
    }

    public <T, R>Function<T, Optional<R>> apply(TryableFunction<T, R> function){
        return t -> {
            try {
                return Optional.ofNullable(function.apply(t));
            } catch (Exception e) {
                callback.accept(e);
                return Optional.empty();
            }
        };
    }
}
