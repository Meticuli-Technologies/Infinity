package com.meti.lib.fx;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/27/2019
 */
public interface Wizard<T> {
    String getName();

    void open();
    boolean isRunning();
    void close();

    T getResult();
}
