package com.meti.lib.asset;

import com.meti.lib.asset.source.Source;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface AssetTranslator<A extends Asset<?, ?>> {
    A read(Source source) throws IOException;

    void write(Source source, A asset);

    boolean canBuild(Source source);
}
