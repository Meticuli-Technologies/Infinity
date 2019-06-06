package com.meti.lib.asset;

import com.meti.lib.asset.source.Source;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface AssetBuilder<A extends Asset> {
    A build(Source source);

    boolean canBuild(Source source);
}
