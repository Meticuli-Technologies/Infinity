package com.meti.chat;

import com.meti.lib.net.Update;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ChatUpdate implements Update {
    public final Message latest;

    public ChatUpdate(Message latest) {
        this.latest = latest;
    }
}
