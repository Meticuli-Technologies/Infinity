package com.meti.lib.asset.text;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public class SerializedTextAssetChange implements TextAssetChange {
    private static final long serialVersionUID = -303601291311302480L;
    private final int start;
    private final int stop;
    private final String value;
    private final boolean wasAdded;
    private final boolean wasRemoved;
    private final String name;

    public SerializedTextAssetChange(String name, int start, String value) {
        this(name, start, -1, value, true, false);
    }

    private SerializedTextAssetChange(String name, int start, int stop, String value, boolean wasAdded, boolean wasRemoved) {
        this.name = name;
        this.start = start;
        this.stop = stop;
        this.value = value;
        this.wasAdded = wasAdded;
        this.wasRemoved = wasRemoved;
    }

    public SerializedTextAssetChange(String name, int start, int stop) {
        this(name, start, stop, "", false, true);
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
    public String getValue() {
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

    @Override
    public String getName() {
        return name;
    }
}
