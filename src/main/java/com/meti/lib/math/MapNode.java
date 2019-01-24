package com.meti.lib.math;

import java.util.HashMap;
import java.util.Map;

public class MapNode<T, V extends VectorNode<T, V>> implements VectorNode<T, V> {
    public final Map<T, V> map = new HashMap<>();

    @Override
    public V get(V vector) {
        return map.get(vector);
    }
}
