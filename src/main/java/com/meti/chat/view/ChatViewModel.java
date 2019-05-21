package com.meti.chat.view;

import com.meti.chat.ChatMessage;
import com.meti.fx.FXConsumerHandler;
import com.meti.fx.Injector;
import com.meti.handle.TokenHandler;
import com.meti.net.client.Client;
import com.meti.net.client.ClientViewModel;
import com.meti.net.source.URLSource;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.logging.Logger;

public class ChatViewModel extends ClientViewModel {
    private final FXConsumerHandler<ChatMessage> handler = new ChatMessageHandler();

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
        Injector injector = new Injector(URLSource.of("/com/meti/ChatView.fxml"), logger, client);
        Parent load = injector.load();
        Consumer<ChatMessage> consumer = ((ChatView) injector.getController()).getMessageConsumer();
        handler.setConsumer(consumer);
        return load;
    }
}
