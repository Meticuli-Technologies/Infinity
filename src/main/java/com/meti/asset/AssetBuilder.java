package com.meti.asset;

import com.meti.net.source.Source;

import java.io.IOException;

interface AssetBuilder<A extends Asset> {
    A build(Source source) throws IOException;

    boolean canBuild(Source source);
}
