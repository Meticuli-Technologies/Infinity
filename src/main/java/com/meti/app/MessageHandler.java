package com.meti.app;

import com.meti.lib.OKResponse;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
class MessageHandler implements Function<Object, OKResponse> {
    private final Consumer<Message> messageConsumer;

    MessageHandler(Consumer<Message> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public OKResponse apply(Object o) {
        Message message = (Message) o;
        messageConsumer.accept(message);
        return new OKResponse();
    }
}
