package com.meti.lib.asset;

import com.meti.lib.asset.properties.AssetProperties;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Asset<C extends AssetChange, V> extends Serializable {
    AssetProperties getProperties();

    V getValue();

    BiConsumer<C, Asset<C, V>> getOnChange();

    void change(C change);

    void setOnChange(BiConsumer<C, Asset<C, V>> consumer);
}