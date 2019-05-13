package com.meti;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatView {
    private final Logger logger;
    private final Client client;
    @FXML
    private ListView<String> output;
    @FXML
    private TextField input;

    public ChatView(Logger logger, Client client, InfinityClientTokenHandler handler) {
        this.logger = logger;
        this.client = client;
        handler.handlers.add(new TypeTokenHandler<>(ChatMessage.class) {
            @Override
            protected void handleGeneric(ChatMessage token) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        output.getItems().add(token.getValue());
                    }
                });
            }
        });
    }

    @FXML
    public void send() {
        ChatMessage message = new ChatMessage(input.getText());
        try {
            client.write(message);
            client.flush();
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        input.clear();
    }
}
