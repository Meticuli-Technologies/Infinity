package com.meti.lib.collect.tryable;

import com.meti.lib.collect.catches.Catcher;
import com.meti.lib.collect.catches.CollectionCatcher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class TryableFactory<C extends Catcher> {
    public static final TryableFactory<CollectionCatcher<ArrayList<Exception>>> DEFAULT = new TryableFactory<>(new CollectionCatcher<>(new ArrayList<>()));
    public final C catcher;

    public TryableFactory(C catcher) {
        this.catcher = catcher;
    }

    public Supplier<Optional<Exception>> accept(Tryable tryable) {
        return () -> {
            try {
                tryable.perform();
                return Optional.empty();
            } catch (Exception e) {
                catcher.accept(e);
                return Optional.of(e);
            }
        };
    }


    public <T, R> Function<T, Optional<R>> apply(TryableFunction<T, R> function) {
        return t -> {
            try {
                return Optional.ofNullable(function.apply(t));
            } catch (Exception e) {
                catcher.accept(e);
                return Optional.empty();
            }
        };
    }


    public <T> Supplier<Optional<T>> supplier(TryableSupplier<T> supplier) {
        return () -> {
            try {
                return Optional.ofNullable(supplier.get());
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
}
