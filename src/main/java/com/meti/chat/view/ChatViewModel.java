package com.meti.chat.view;

import com.meti.net.client.Client;
import com.meti.net.client.ClientViewModel;
import com.meti.net.client.InfinityClientTokenHandler;
import com.meti.fx.Injector;
import com.meti.net.source.URLSource;
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
