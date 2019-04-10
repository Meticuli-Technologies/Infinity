package com.meti.lib.fx;

import javafx.application.Platform;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/6/2019
 */
public class FXUtil {
    private FXUtil(){
    }

    public static void throwIfNotFX(){
        if (!Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Not on the JavaFX thread!");
        }
    }

    public static <T> Future<T> call(Callable<T> callable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            try {
                future.complete(callable.call());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        });
        return future;
    }
}
