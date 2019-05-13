package com.meti;

import javafx.scene.Parent;

import java.io.IOException;
import java.util.logging.Logger;

public class ChatViewModel extends ClientViewModel {
    public ChatViewModel(Logger logger, Client client, InfinityClientTokenHandler handler) {
        super(logger, client, handler);
    }

    @Override
    public String getName() {
        return "Chat";
    }

    @Override
    public Parent getRoot() throws IOException {
        return Injector.loadStatic(URLSource.of("/com/meti/ChatView.fxml"), logger, client, handler);
    }
}
