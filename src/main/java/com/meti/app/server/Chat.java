package com.meti.app.server;

import com.meti.app.control.Message;
import com.meti.lib.event.Component;
import com.meti.lib.trys.CollectionConsumer;

import java.util.ArrayList;

import static com.meti.app.server.ChatEvent.ADDED;

public class Chat extends Component<ChatEvent> {
    {
        eventManager.compound(ADDED, new CollectionConsumer<>(new ArrayList<>()));
    }

    public void add(Message message) {
        eventManager.fireEvent(ADDED, new ChatEvent(message, true, false));
    }
}
