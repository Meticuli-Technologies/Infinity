package com.meti.chat;

import com.meti.lib.net.AbstractHandler;
import com.meti.lib.net.Handler;
import com.meti.lib.net.OKResponse;
import com.meti.lib.util.TypeFunction;
import com.meti.lib.util.TypePredicate;

import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class Chat {
    private final LinkedList<Message> messages = new LinkedList<>();
    public Consumer<Message> onMessageAdded;

    public boolean add(Message message) {
        if (onMessageAdded != null) {
            onMessageAdded.accept(message);
        }
        return messages.add(message);
    }

    public Message poll() {
        return messages.poll();
    }

    public Handler<Object> getRegisteredMessageHandler() {
        return new AbstractHandler<>(
                new TypePredicate<>(Message.class),
                new TypeFunction<>(Message.class).andThen((Function<Message, OKResponse>) message -> {
                    add(message);
                    return new OKResponse("Message received successfully.");
                })
        );
    }

    public Handler<Object> getRequestHandler() {
        return new AbstractHandler<>(
                new TypePredicate<>(ChatRequest.class),
                new TypeFunction<>(ChatRequest.class).andThen((Function<ChatRequest, ChatUpdate>) chatRequest ->
                        new ChatUpdate(poll())
                )
        );
    }
}
