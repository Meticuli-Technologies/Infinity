package com.meti.chat.view;

import com.meti.chat.ChatMessage;
import com.meti.net.client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatView {
    private final Logger logger;
    private final Client client;
    @FXML
    private ListView<String> output;
    @FXML
    private TextField input;

    public ChatView(Logger logger, Client client) {
        this.logger = logger;
        this.client = client;
    }

    public Consumer<ChatMessage> getMessageConsumer() {
        return chatMessage -> output.getItems().add(chatMessage.getValue());
    }

    @FXML
    public void send() {
        ChatMessage message = new ChatMessage(input.getText());
        tryWriteMessage(message);
        input.clear();
    }

    private void tryWriteMessage(ChatMessage message) {
        try {
            writeImpl(message);
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    private void writeImpl(ChatMessage message) throws IOException {
        client.write(message);
        client.flush();
    }
}
