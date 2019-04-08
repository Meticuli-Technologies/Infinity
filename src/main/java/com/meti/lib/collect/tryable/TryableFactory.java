package com.meti.lib.collect.tryable;

import com.meti.lib.collect.catches.Catcher;

import java.util.Optional;
import java.util.function.Supplier;

class TryableFactory {
    Catcher catcher;

    public TryableFactory(Catcher catcher) {
        this.catcher = catcher;
    }

    public <T> Supplier<Optional<T>> supplier(TryableSupplier<T> supplier){
        return () -> {
            try {
                return Optional.ofNullable(supplier.get());
            } catch (Exception e) {
                return Optional.empty();
            }
        };
    }
}
