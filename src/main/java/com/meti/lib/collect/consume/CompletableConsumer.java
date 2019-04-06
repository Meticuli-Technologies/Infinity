package com.meti.lib.collect.consume;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
public class CompletableConsumer<T> extends CompletableFuture<T> implements Consumer<T> {
    @Override
    public void accept(T t) {
        complete(t);
    }
}
