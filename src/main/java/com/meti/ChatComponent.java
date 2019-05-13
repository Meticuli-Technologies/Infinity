package com.meti;

import java.util.Collection;
import java.util.Collections;

public class ChatComponent implements ClientComponent {
    private final Chat chat = new Chat();

    @Override
    public Collection<? extends TokenHandler> getHandlers() {
        return Collections.singleton(new ChatHandler(chat));
    }
}
