package com.meti.app;

import java.util.concurrent.CompletableFuture;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public interface Querier {
    @SuppressWarnings("AccessingNonPublicFieldOfAnotherObject")
    <T> CompletableFuture<T> query(Class<T> returnClass);
}
