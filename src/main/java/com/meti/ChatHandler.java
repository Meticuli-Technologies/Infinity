package com.meti;

public class ChatHandler extends TypeTokenHandler<ChatMessage> {
    private final Chat chat;

    public ChatHandler(Chat chat) {
        super(ChatMessage.class);
        this.chat = chat;
    }

    @Override
    protected void handleGeneric(ChatMessage token) {
        chat.log(token);
    }
}
