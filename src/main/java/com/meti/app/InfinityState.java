package com.meti.app;

import com.meti.lib.asset.SetBasedAssetManager;
import com.meti.lib.collect.SetBasedState;
import com.meti.lib.javafx.ListBasedStageManager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public final class InfinityState extends SetBasedState {
    public InfinityState() {
        add(new ListBasedStageManager());
        add(new SetBasedAssetManager());
    }
}
