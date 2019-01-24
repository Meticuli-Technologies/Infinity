package com.meti.lib.math;

import java.util.HashMap;
import java.util.Map;

public class VectorGrid<T> {
    public final Map<Vector, T> content = new HashMap<>();

    private Vector current;

    public VectorGrid() {
        this(2);
    }

    public VectorGrid(int dimensions) {
        this.current = Vector.empty(dimensions);
    }

    public T move(Vector direction) {
        if (current == null) {
            throw new IllegalStateException("Why is current null?");
        }

        Vector transformed = current.add(direction);
        T result = content.get(transformed);
        if (result == null) {
            throw new IllegalArgumentException("Could not find anything at  " + transformed);
        }

        current = transformed;
        return result;
    }
}