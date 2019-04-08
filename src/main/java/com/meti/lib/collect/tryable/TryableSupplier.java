package com.meti.lib.collect.tryable;

import java.util.Optional;

public interface TryableSupplier<T> {
    Optional<T> get();

    default T next(){
        Optional<T> optional;
        do {
            optional = get();
        }
        while(!optional.isPresent());

        return optional.get();
    }
}
