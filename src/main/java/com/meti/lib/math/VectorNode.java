package com.meti.lib.math;

public interface VectorNode<T, V extends VectorNode<T, V>> {
    T get(V vector);
}
