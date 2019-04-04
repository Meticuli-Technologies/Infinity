package com.meti.lib.trys;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/31/2019
 */
public class TryableFactory<C extends Catcher> {
    public static final TryableFactory<CollectionCatcher<Set<Exception>>> DEFAULT_FACTORY = new TryableFactory<>(new CollectionCatcher<>(new HashSet<>()));
    public final C catcher;

    public TryableFactory(C catcher) {
        this.catcher = catcher;
    }

    public <T> Consumer<T> newConsumer(TryableConsumer<T> tryableConsumer) {
        return t -> {
            try {
                tryableConsumer.accept(t);
            } catch (Exception e) {
                catcher.accept(e);
            }
        };
    }

    public <T, R> Function<T, R> newFunction(TryableFunction<T, R> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                catcher.accept(e);
                return null;
            }
        };
    }
}
