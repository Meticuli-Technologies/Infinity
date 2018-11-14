package com.meti.lib.net.connect;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2018
 */
public class SocketConnection extends ObjectConnection {
    public final Socket socket;

    public SocketConnection(Socket socket) throws IOException {
        super();
        this.socket = socket;
    }

    @Override
    public InputStream constructInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public OutputStream constructOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    @Override
    public void closeSource() throws IOException {
        socket.close();
    }
}
