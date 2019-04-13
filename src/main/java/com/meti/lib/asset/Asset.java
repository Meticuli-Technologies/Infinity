package com.meti.lib.asset;

import java.io.IOException;

public abstract class Asset {
    @Override
    public String toString() {
        return getName();
    }

    public abstract String getName();

    public abstract long size() throws IOException;
}
