package com.meti.app.server;

import com.meti.app.control.Message;
import com.meti.lib.collection.TypeFunction;
import com.meti.lib.collection.TypePredicate;
import com.meti.lib.event.NetworkedComponent;
import com.meti.lib.net.Client;
import com.meti.lib.net.handle.AbstractTokenHandler;
import com.meti.lib.net.handle.TokenHandler;
import com.meti.lib.net.query.OKResponse;
import com.meti.lib.trys.CollectionConsumer;

import java.util.ArrayList;
import java.util.function.Function;

import static com.meti.app.server.ChatEvent.ADDED;
import static com.meti.app.server.ChatEvent.ALL;

public class Chat extends NetworkedComponent<ChatEvent> {
    private final ArrayList<Message> messages = new ArrayList<>();

    {
        eventManager.compound(ADDED, new CollectionConsumer<>(new ArrayList<>()));
    }

    public void add(Message message) {
        eventManager.fireEvent(ADDED, new ChatEvent(message));
        messages.add(message);
    }

    public AbstractTokenHandler<Object> messageHandler() {
        return new AbstractTokenHandler<>(
                new TypePredicate<>(Message.class),
                ((Function<Message, Object>) message -> {
                    add(message);
                    return new OKResponse("Received message \"" + message.value + "\" successfully.");
                }).compose(new TypeFunction<>(Message.class)));
    }

    public TokenHandler<Object> requestHandler(Client<?> client) {
        confirm(client).register(ALL);
        return new AbstractTokenHandler<>(
                new TypePredicate<>(ChatRequest.class),
                ((Function<ChatRequest, Object>) request ->
                        confirm(client).getUpdate(ALL))
                        .compose(new TypeFunction<>(ChatRequest.class)));
    }
}
