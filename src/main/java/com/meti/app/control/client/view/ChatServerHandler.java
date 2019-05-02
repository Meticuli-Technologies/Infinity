package com.meti.app.control.client.view;

import com.meti.app.io.ServerHandler;
import com.meti.lib.io.respond.OKResponse;
import com.meti.lib.io.server.handle.TokenHandler;
import com.meti.lib.io.server.handle.TypeTokenHandler;
import com.meti.lib.io.source.Source;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public class ChatServerHandler implements ServerHandler {
    @Override
    public Collection<? extends TokenHandler<Object, ?>> getHandlers() {
        return Collections.singletonList(
                new TypeTokenHandler<>(ChatMessage.class) {
                    @Override
                    protected Object handle(ChatMessage chatMessage, Source source) {
                        return new OKResponse("Message received.");
                    }
                }
        );
    }
}
