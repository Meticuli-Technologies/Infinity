package com.meti.lib.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/29/2019
 */
public class Counter {
    private final int initialValue;
    private final boolean direction;
    private int value;

    public Counter() {
        this(0, true);
    }

    public Counter(int initialValue, boolean direction) {
        this.initialValue = initialValue;
        this.direction = direction;
        this.value = initialValue;
    }

    public Counter(int initialValue) {
        this(initialValue, true);
    }

    public Counter(boolean direction) {
        this(0, direction);
    }

    public int adjust() {
        int previousValue = value;
        if (direction) {
            this.value++;
        } else {
            this.value--;
        }
        return previousValue;
    }

    public int get() {
        return value;
    }

    public void reset() {
        this.value = initialValue;
    }
}
