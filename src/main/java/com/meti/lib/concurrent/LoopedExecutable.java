package com.meti.lib.concurrent;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/31/2019
 */
public abstract class LoopedExecutable extends Executable {
    private boolean running;

    @Override
    public Void call() throws Exception {
        running = true;
        while (shouldContinue()) {
            loop();
        }
        return null;
    }

    private boolean shouldContinue() {
        return running;
    }

    protected abstract void loop() throws IOException;

    protected void stop() {
        running = false;
    }
}
