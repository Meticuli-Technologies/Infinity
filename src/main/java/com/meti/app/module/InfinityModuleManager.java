package com.meti.app.module;

import com.meti.app.control.client.view.ChatClientView;
import com.meti.app.control.client.view.ChatServerHandler;
import com.meti.lib.module.CollectionModule;
import com.meti.lib.module.ModuleManager;

public class InfinityModuleManager extends ModuleManager {
    {
        add(new CollectionModule("ChatController", ChatClientView.class, ChatServerHandler.class));
    }
}
