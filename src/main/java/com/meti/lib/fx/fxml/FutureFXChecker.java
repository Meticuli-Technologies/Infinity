package com.meti.lib.fx.fxml;

import com.meti.lib.io.server.Server;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public abstract class FutureFXChecker extends AnimationTimer implements Consumer<Exception> {
    private final Future<Server> future;

    protected FutureFXChecker(Future<Server> future) {
        this.future = future;
    }

    @Override
    public void handle(long now) {
        checkFuture();
    }

    private void checkFuture() {
        if (future.isDone()) {
            handleResult();
        }
    }

    private void handleResult() {
        try {
            future.get();
        } catch (Exception e) {
            accept(e);
            Platform.exit();
        }
    }
}
