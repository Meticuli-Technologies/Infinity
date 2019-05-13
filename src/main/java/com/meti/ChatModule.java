package com.meti;

public class ChatModule extends CollectionModule {
    public ChatModule() {
        super("Chat", ChatComponent.class, ChatViewModel.class);
    }
}
