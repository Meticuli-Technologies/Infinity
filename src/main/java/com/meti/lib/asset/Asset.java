package com.meti.lib.asset;

import com.meti.lib.asset.properties.AssetProperties;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Asset<C extends AssetChange, V> {
    AssetProperties getProperties();

    V getValue();

    void change(C change);

    void onChange(BiConsumer<C, Asset<C, V>> consumer);
}