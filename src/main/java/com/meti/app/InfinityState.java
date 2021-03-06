package com.meti.app;

import com.meti.lib.asset.manage.SetBasedAssetManager;
import com.meti.lib.collect.SetBasedState;
import com.meti.lib.javafx.ListBasedStageManager;
import com.meti.lib.module.SetBasedModuleManager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/3/2019
 */
public final class InfinityState extends SetBasedState {
    public InfinityState() {
        add(new ListBasedStageManager());
        add(new SetBasedAssetManager());
        add(new InfinityModuleManager());
    }
}
