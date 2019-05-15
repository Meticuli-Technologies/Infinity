package com.meti.module;

import com.meti.chat.ChatModule;

public class InfinityModuleManager extends ModuleManager {
    public InfinityModuleManager() {
        addModule(new ChatModule());
    }
}
