package com.meti.net.server;

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
        while (running) {
            loop();
        }
        return null;
    }

    protected abstract void loop() throws IOException;

    public void stop() {
        running = false;
    }
}
