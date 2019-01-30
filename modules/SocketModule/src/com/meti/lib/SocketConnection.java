package com.meti.lib;

import com.meti.lib.net.SimpleConnection;

import java.io.IOException;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 1/30/2019
 */
public class SocketConnection extends SimpleConnection {
    private final Socket socket;

    public SocketConnection(Socket socket) throws IOException {
        super(socket.getInputStream(), socket.getOutputStream());
        this.socket = socket;
    }

    @Override
    public void close() throws IOException {
        super.close();

        socket.close();
    }
}
