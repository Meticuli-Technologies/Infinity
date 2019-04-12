package com.meti.lib.asset;

import java.io.IOException;

public interface Asset {
    String getName();

    Asset getParent();

    long size() throws IOException;
}
