package com.meti.lib.fx.fxml;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public abstract class FutureFXChecker extends AnimationTimer implements Consumer<Exception> {
    public final List<Future<?>> futures = new ArrayList<>();

    protected FutureFXChecker(Future<?>... futures) {
        this.futures.addAll(Arrays.asList(futures));
    }

    @Override
    public void handle(long now) {
        for (Future<?> future : futures) {
            checkFuture(future);
        }
    }

    private void checkFuture(Future<?> future) {
        if (future.isDone()) {
            handleResult(future);
        }
    }

    private void handleResult(Future<?> future) {
        try {
            future.get();
        } catch (Exception e) {
            accept(e);
            Platform.exit();
        }
    }
}
