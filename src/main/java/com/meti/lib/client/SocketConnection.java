package com.meti.lib.client;

import java.io.IOException;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/27/2018
 */
public class SocketConnection extends Connection {
    public final Socket socket;

    public SocketConnection(Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this.socket = socket;
    }
}
