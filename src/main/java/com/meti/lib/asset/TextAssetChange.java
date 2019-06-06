package com.meti.lib.asset;

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
