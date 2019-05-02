package com.meti.app.control.client.view;

import com.meti.app.io.update.Update;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 5/2/2019
 */
public class ChatUpdate implements Update {
    final String value;

    public ChatUpdate(String value) {
        this.value = value;
    }
}
