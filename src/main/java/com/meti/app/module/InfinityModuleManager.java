package com.meti.app.module;

import com.meti.app.control.client.view.ChatView;
import com.meti.lib.module.CollectionModule;
import com.meti.lib.module.ModuleManager;

public class InfinityModuleManager extends ModuleManager {
    {
        add(new CollectionModule("Chat", ChatView.class));
    }
}
