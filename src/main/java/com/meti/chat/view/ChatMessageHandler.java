package com.meti.chat.view;

import com.meti.chat.ChatMessage;
import com.meti.handle.TypeTokenHandler;
import javafx.application.Platform;
import javafx.scene.control.ListView;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/13/2019
 */
class ChatMessageHandler extends TypeTokenHandler<ChatMessage> {
    private ListView<String> output;

    public ChatMessageHandler() {
        super(ChatMessage.class);
    }

    @Override
    protected void handleGeneric(ChatMessage token) {
        Platform.runLater(() -> output.getItems().add(token.getValue()));
    }

    public void setOutput(ListView<String> output) {
        this.output = output;
    }
}
