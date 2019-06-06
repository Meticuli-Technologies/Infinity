package com.meti.lib.asset;

import java.util.function.BiConsumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface Asset<A extends AssetComponents, C extends AssetChange> {
    String getName();

    void change(C change);

    A getChanges();

    void onChange(BiConsumer<C, Asset<A, C>> consumer);
}
