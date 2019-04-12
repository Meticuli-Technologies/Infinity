package com.meti.app.chat;

import com.meti.lib.event.ServerComponent;
import com.meti.lib.net.AbstractHandler;
import com.meti.lib.net.Client;
import com.meti.lib.net.Handler;
import com.meti.lib.net.OKResponse;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Chat extends ServerComponent<ChatEvent, Message> {
    private final LinkedList<Message> messages = new LinkedList<>();

    @Override
    public Stream<? extends Handler<Object>> getHandlers(Client client) {
        return Stream.of(
                new AbstractHandler<>(
                        new TypePredicate<>(Message.class),
                        new TypeFunction<>(Message.class).andThen((Function<Message, OKResponse>) message -> {
                            add(message);
                            return new OKResponse("Message received successfully.");
                        })
                ),
                new AbstractHandler<>(
                        new TypePredicate<>(ChatRequest.class),
                        new TypeFunction<>(ChatRequest.class).andThen((Function<ChatRequest, ChatUpdate>) chatRequest ->
                                new ChatUpdate(poll())
                        )
                )
        );
    }

    public boolean add(Message message) {
        eventManager.fire(ChatEvent.ON_ADDED, message);
        return messages.add(message);
    }

    public Message poll() {
        return messages.poll();
    }
}
