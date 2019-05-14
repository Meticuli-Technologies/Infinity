package com.meti.chat.view;

import com.meti.fx.Injector;
import com.meti.handle.TokenHandler;
import com.meti.net.client.Client;
import com.meti.net.client.ClientViewModel;
import com.meti.net.client.InfinityClientTokenHandler;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class ChatViewModel extends ClientViewModel {
    private final ChatMessageHandler handler = new ChatMessageHandler();

    public ChatViewModel(Logger logger, Client client) {
        super(logger, client);
    }

    @Override
    public String getName() {
        return "Chat";
    }

    @Override
    public Collection<? extends TokenHandler> getHandlers() {
        return Collections.singleton(handler);
    }

    @Override
    public Parent getRoot() throws IOException {
        return Injector.loadStatic(URLSource.of("/com/meti/ChatView.fxml"), logger, client, handler);
    }
}
