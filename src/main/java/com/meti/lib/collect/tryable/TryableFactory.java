package com.meti.lib.collect.tryable;

import com.meti.lib.collect.catches.Catcher;
import com.meti.lib.collect.catches.CollectionCatcher;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class TryableFactory {
    public static final TryableFactory DEFAULT = new TryableFactory(new CollectionCatcher<>(new ArrayList<>()));
    final Catcher catcher;

    public TryableFactory(Catcher catcher) {
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
