package com.meti.lib.util;

import java.util.Optional;
import java.util.concurrent.Callable;

public abstract class Loop implements Callable<Optional<Exception>> {
    @Override
    public Optional<Exception> call() {
        try {
            start();

            while (!Thread.interrupted()) {
                loop();
            }

            stop();

            return Optional.empty();
        } catch (Exception e) {
            return Optional.of(e);
        }
    }

    public void start() {
    }

    public abstract void loop() throws Exception;

    public void stop() {
    }
}
