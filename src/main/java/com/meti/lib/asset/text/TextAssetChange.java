package com.meti.lib.asset.text;

import com.meti.lib.asset.AssetChange;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/6/2019
 */
public interface TextAssetChange extends AssetChange {
    int getStart();

    int getStop();

    char getValue();

    boolean wasAdded();

    boolean wasRemoved();
}
