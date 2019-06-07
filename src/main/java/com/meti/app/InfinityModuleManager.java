package com.meti.app;

import com.meti.app.text.TextModule;
import com.meti.lib.module.SetBasedModuleManager;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 6/7/2019
 */
public final class InfinityModuleManager extends SetBasedModuleManager {
    public InfinityModuleManager() {
        add(new TextModule());
    }
}
