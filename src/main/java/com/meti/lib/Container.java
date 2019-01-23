package com.meti.lib;

import java.util.Collection;

public interface Container<T, C extends Collection<T>> {
    C getElements();
    T toSingle();
}
