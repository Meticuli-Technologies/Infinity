package com.meti.lib.net.connect;

import java.io.IOException;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2018
 */
public class SocketConnection extends ObjectConnection {
    public final Socket socket;

    public SocketConnection(Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this.socket = socket;
    }

    @Override
    public void closeSource() throws IOException {
        socket.close();
    }
}
