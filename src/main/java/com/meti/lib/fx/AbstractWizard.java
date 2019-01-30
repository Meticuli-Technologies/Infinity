package com.meti.lib.fx;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class AbstractWizard<T> implements Wizard<T> {
    private final String name;
    private boolean running;

    public AbstractWizard(String name) {
        this.name = name;
    }

    @Override
    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public void open() {
        running = true;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void close() {
        running = false;
    }
}
