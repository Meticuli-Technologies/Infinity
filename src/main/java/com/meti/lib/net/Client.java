package com.meti.lib.net;

import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class Client {
    private final Socket socket;

    public Client(Socket socket) {
        this.socket = socket;
    }
}
