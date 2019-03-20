package com.meti.lib.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class QueuedFunction<T> implements Function<T, Void> {
    public final Queue<T> queue = new LinkedList<>();

    @Override
    public Void apply(T t) {
        queue.add(t);
        return null;
    }
}
