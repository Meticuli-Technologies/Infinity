package com.meti.lib.asset;

import com.meti.lib.source.NamedParentSource;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/22/2019
 */
public interface AssetManagerImpl {
    <T extends NamedParentSource<T>> void load(T source) throws IOException;
}
