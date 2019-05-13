package com.meti;

public class ChatModule extends CollectionModule {
    public ChatModule(String name, Class<?>... classes) {
        super("Chat", ChatComponent.class
        );
    }
}
