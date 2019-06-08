package com.meti.app;

import com.meti.lib.net.TypeHandler;
import com.meti.lib.net.client.Client;
import com.meti.lib.net.client.handle.ResponseProcessor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public class MapBasedQuerier implements Querier {
    private final Map<Class<?>, BlockingHandler<?>> handleMap = new HashMap<>();

    private final ResponseProcessor processor;

    public MapBasedQuerier(ResponseProcessor processor) {
        this.processor = processor;
    }

    @Override
    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    public <T> CompletableFuture<T> query(Class<T> returnClass) {
        BlockingHandler<?> handler;
        if (handleMap.containsKey(returnClass)) {
            handler = handleMap.get(returnClass);
        } else {
            handler = new BlockingHandler<>(returnClass);
            processor.addHandler(handler);
            handleMap.put(returnClass, handler);
        }

        CompletableFuture<Object> future = new CompletableFuture<>();
        handler.getFutures().add(future);
        return future.thenApply(returnClass::cast);
    }

    private static class BlockingHandler<T> extends TypeHandler<T> {
        //TODO: allow for change in default capacity
        private final ArrayBlockingQueue<CompletableFuture<Object>> futures = new ArrayBlockingQueue<>(16);

        protected BlockingHandler(Class<T> tClass) {
            super(tClass);
        }

        @Override
        public Optional<Serializable> handleGeneric(T response, Client client) {
            try {
                futures.take().complete(response);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return Optional.empty();
        }

        public ArrayBlockingQueue<CompletableFuture<Object>> getFutures() {
            return futures;
        }
    }
}
