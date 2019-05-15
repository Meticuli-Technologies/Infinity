package com.meti.chat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

class Chat {
    private final ObservableList<ChatMessage> messages = FXCollections.observableArrayList();

    public void log(ChatMessage token) {
        messages.add(token);
    }
}
