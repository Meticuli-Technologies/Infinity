package com.meti;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 4/11/2019
 */
public class ChatUpdate implements Update {
    final Message latest;

    public ChatUpdate(Message latest) {
        this.latest = latest;
    }
}
