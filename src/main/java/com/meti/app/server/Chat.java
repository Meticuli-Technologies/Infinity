package com.meti.app.server;

import com.meti.app.control.Message;
import com.meti.lib.collection.TypeFunction;
import com.meti.lib.collection.TypePredicate;
import com.meti.lib.event.Component;
import com.meti.lib.net.handle.AbstractTokenHandler;
import com.meti.lib.net.handle.TokenHandler;
import com.meti.lib.net.query.OKResponse;
import com.meti.lib.net.query.Update;
import com.meti.lib.trys.CollectionConsumer;

import java.util.ArrayList;
import java.util.function.Function;

import static com.meti.app.server.ChatEvent.ADDED;

public class Chat extends Component<ChatEvent> {
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

    public TokenHandler<Object> requestHandler() {
        return new AbstractTokenHandler<>(new TypePredicate<>(ChatRequest.class), ((Function<ChatRequest, Object>) o -> {
            if (o.key == ChatEvent.ADDED) {
                return Update.of(eventManager.getUpdates(ChatEvent.ADDED));
            } else if (o.key == ChatEvent.REMOVED) {
                return Update.of(eventManager.getUpdates(ChatEvent.REMOVED));
            } else {
                return new IllegalArgumentException("Cannot process ChatRequest " + o.toString() + ".");
            }
        }).compose(new TypeFunction<>(ChatRequest.class)));
    }
}
