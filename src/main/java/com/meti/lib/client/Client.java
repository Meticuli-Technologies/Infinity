package com.meti.lib.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 10/21/2018
 */
public class Client {
    public final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    private boolean closed = false;

    public Client(Socket socket) throws IOException {
        this.socket = socket;

        //construction is reversed because the header would cause problems
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean close() throws IOException {
        if (!closed) {
            inputStream.close();
            outputStream.close();

            socket.close();
            return true;
        }
        else{
            return false;
        }
    }
}
