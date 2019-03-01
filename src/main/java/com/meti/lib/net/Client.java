package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 2/28/2019
 */
public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(Socket socket) throws IOException {
        this.socket = socket;

        //these two items are reversed b/c the ObjectInputStream must flush the header
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public Command<?> read() throws Exception {
        Object token = inputStream.readObject();
        if (token instanceof Command<?>) {
            return (Command) token;
        } else {
            throw new IllegalStateException("Token " + token + " is not an instance of " + Command.class);
        }
    }

    public void write(Command<?> command) throws IOException {
        outputStream.writeObject(command);
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}