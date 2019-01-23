package com.meti.lib.bucket;

import java.util.Collection;
import java.util.function.Consumer;

public interface Container<T, C extends Collection<T>> extends Consumer<T> {
    C getElements();

    T toSingle();
}
