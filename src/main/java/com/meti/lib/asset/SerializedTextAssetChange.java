package com.meti.lib.asset;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedTextAssetChange implements TextAssetChange {
    private final int start;
    private final int stop;
    private final char value;
    private final boolean wasAdded;
    private final boolean wasRemoved;

    public SerializedTextAssetChange(int start, char value) {
        this(start, -1, value, true, false);
    }

    private SerializedTextAssetChange(int start, int stop, char value, boolean wasAdded, boolean wasRemoved) {
        this.start = start;
        this.stop = stop;
        this.value = value;
        this.wasAdded = wasAdded;
        this.wasRemoved = wasRemoved;
    }

    public SerializedTextAssetChange(int start, int stop) {
        this(start, stop, '\0', false, true);
    }

    @Override
    public int getStart() {
        return start;
    }

    @Override
    public int getStop() {
        return stop;
    }

    @Override
    public char getValue() {
        return value;
    }

    @Override
    public boolean wasAdded() {
        return wasAdded;
    }

    @Override
    public boolean wasRemoved() {
        return wasRemoved;
    }
}
