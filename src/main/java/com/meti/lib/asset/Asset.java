package com.meti.lib.asset;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Asset<C extends AssetChange, V> {
    String getName();

    void change(C change);

    V getValue();

    void onChange(BiConsumer<C, Asset<C, V>> consumer);
}
