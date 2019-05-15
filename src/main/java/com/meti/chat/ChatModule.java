package com.meti.chat;

import com.meti.module.CollectionModule;
import com.meti.chat.view.ChatViewModel;

public class ChatModule extends CollectionModule {
    public ChatModule() {
        super("Chat", ChatComponent.class, ChatViewModel.class);
    }
}
