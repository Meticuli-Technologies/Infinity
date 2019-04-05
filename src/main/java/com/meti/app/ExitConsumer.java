package com.meti.app;

import com.meti.lib.log.ConsoleEvent;

import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/5/2019
 */
class ExitConsumer implements Consumer<ConsoleEvent> {
    private Runnable runnable;

    ExitConsumer(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void accept(ConsoleEvent consoleEvent) {
        if (consoleEvent.level.equals(Level.SEVERE)) {
            runnable.run();
        }
    }
}
