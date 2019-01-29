package com.meti.lib.fx;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class AbstractWizard<T> implements Wizard<T> {
    private final String name;
    private boolean running;

    protected AbstractWizard(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
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
