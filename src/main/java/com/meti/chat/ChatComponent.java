package com.meti.chat;

import com.meti.net.client.ClientComponent;
import com.meti.handle.TokenHandler;

import java.util.Collection;
import java.util.Collections;

public class ChatComponent implements ClientComponent {
    private final Chat chat = new Chat();

    @Override
    public Collection<? extends TokenHandler> getHandlers() {
        return Collections.singleton(new ChatHandler(chat));
    }
}
