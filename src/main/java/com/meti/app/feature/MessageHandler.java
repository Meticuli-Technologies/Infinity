package com.meti.app.feature;

import com.meti.lib.respond.OKResponse;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/19/2019
 */
public class MessageHandler implements Function<Object, OKResponse> {
    private final Consumer<Message> messageConsumer;

    public MessageHandler(Consumer<Message> messageConsumer) {
        this.messageConsumer = messageConsumer;
    }

    @Override
    public OKResponse apply(Object o) {
        messageConsumer.accept((Message) o);
        return new OKResponse();
    }
}
