package com.meti.lib.fx;

import java.util.Optional;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public abstract class AbstractWizard<P, R> implements Wizard<P, R> {
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
    public void open(P... parameters) {
        setRunning(true);
    }

    public void setRunning(boolean b) {
        running = b;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void close() {
        setRunning(false);
    }
}
