package com.meti.lib.bucket;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public class BufferedConsumer<T> implements Consumer<T> {
    public final Queue<T> queue = new LinkedList<>();

    @Override
    public void accept(T t) {
        queue.add(t);
    }
}
