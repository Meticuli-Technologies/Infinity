package com.meti.lib.asset;

import com.meti.lib.source.NamedSource;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface AssetTransformer {
    boolean canRead(NamedSource source);

    Asset read(NamedSource source);
}
