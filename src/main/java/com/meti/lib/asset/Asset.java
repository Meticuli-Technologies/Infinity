package com.meti.lib.asset;

import java.io.IOException;

public interface Asset {
    String getName();

    long size() throws IOException;
}
