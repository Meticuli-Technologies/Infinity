package com.meti.chat.view;

import com.meti.chat.ChatMessage;
import com.meti.fx.FXConsumerHandler;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/13/2019
 */
class ChatMessageHandler extends FXConsumerHandler<ChatMessage> {
    public ChatMessageHandler() {
        super(ChatMessage.class);
    }
}
