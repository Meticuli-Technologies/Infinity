package com.meti.lib.fx;

import com.meti.lib.io.server.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public abstract class FutureFXChecker extends AnimationTimer implements Consumer<Exception> {
    private final Future<Server> future;

    public FutureFXChecker(Future<Server> future) {
        this.future = future;
    }

    @Override
    public void handle(long now) {
        checkFuture();
    }

    public void checkFuture() {
        if (future.isDone()) {
            handleResult();
        }
    }

    public void handleResult() {
        try {
            future.get();
        } catch (Exception e) {
            accept(e);
            Platform.exit();
        }
    }
}
