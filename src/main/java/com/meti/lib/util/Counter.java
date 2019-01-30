package com.meti.lib.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class Counter {
    private int value;

    public Counter() {
        this(0);
    }

    public Counter(int initialValue) {
        this.value = initialValue;
    }

    public int increment() {
        int previousValue = value;
        this.value++;
        return previousValue;
    }

    public int get() {
        return value;
    }
}
