package com.meti.lib.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    public boolean isOpen() {
        return !isClosed();
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public ObjectChannel sharedChannel() {
        return new SharedObjectChannel(inputStream, outputStream);
    }

    public ObjectChannel unsharedChannel() {
        return new UnsharedChannel(inputStream, outputStream);
    }
}
