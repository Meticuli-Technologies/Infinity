package com.meti.lib.asset;

public abstract class Asset {
    @Override
    public String toString() {
        return getName();
    }

    public abstract String getName();
}
