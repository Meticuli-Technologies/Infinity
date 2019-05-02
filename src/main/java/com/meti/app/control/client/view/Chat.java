package com.meti.app.control.client.view;

import com.meti.app.io.update.Component;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public class Chat extends Component {
    public ChatUpdate writeMessage(ChatMessage message) {
        return new ChatUpdate(message.getValue());
    }
}
