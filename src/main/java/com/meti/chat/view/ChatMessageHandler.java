package com.meti.chat.view;

import com.meti.chat.ChatMessage;
import com.meti.handle.TypeTokenHandler;
import javafx.application.Platform;

import java.util.function.Consumer;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/13/2019
 */
class ChatMessageHandler extends TypeTokenHandler<ChatMessage> {
    private Consumer<ChatMessage> messageConsumer;

    public ChatMessageHandler() {
        super(ChatMessage.class);
    }

    @Override
    protected void handleGeneric(ChatMessage token) {
        if (messageConsumer != null) {
            Platform.runLater(() -> messageConsumer.accept(token));
        } else {
            throw new IllegalStateException("Message consumer has not been set!");
        }
    }

    public void setMessageConsumer(Consumer<ChatMessage> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }
}
