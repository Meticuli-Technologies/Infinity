package com.meti;

import java.io.IOException;

public interface AssetBuilder<A extends Asset> {
    A build(Source source) throws IOException;

    boolean canBuild(Source source);
}
