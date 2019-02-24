package com.meti.lib;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/24/2019
 */
public class TryableFactory {
    private final Callback callback = new Callback();

    public <T> T checkAll(T t) throws Exception {
        Optional<Exception> optional = callback.getAll();
        if (optional.isPresent()) {
            throw optional.get();
        } else {
            return t;
        }
    }

    public <T> T checkFirst(T t) throws Exception {
        Optional<Exception> optional = callback.getFirst();
        if (optional.isPresent()) {
            throw optional.get();
        } else {
            return t;
        }
    }

    public <T, R> Function<T, Optional<R>> apply(TryableFunction<T, R> function) {
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
