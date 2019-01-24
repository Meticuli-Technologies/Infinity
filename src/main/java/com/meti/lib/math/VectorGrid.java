package com.meti.lib.math;

public class VectorGrid<T, V extends VectorNode<T, V>> {
    private V current;

    public VectorGrid(V current) {
        this.current = current;
    }

    public V reload() {
        return current;
    }

    public V move(Vector vector) {
        current = current.get(vector);
        return current;
    }
}