package com.meti;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Closeable {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    Client(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    private Client(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    private Client(Socket socket) throws IOException {
        this.socket = socket;
    /*
            The OOS must be constructed before the OIS because of the header.
             */
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    void readResponse() throws IOException, ClassNotFoundException {
        processResponse(inputStream.readObject());
    }

    private void processResponse(Object response) {
        if (response instanceof Throwable) {
            ((Throwable) response).printStackTrace();
        } else {
            processResponseNonThrowable(response);
        }
    }

    private void processResponseNonThrowable(Object response) {
        if (response instanceof String) {
            System.out.println(response);
        } else {
            throw new UnsupportedOperationException("Unknown response: " + response);
        }
    }

    void writeAndFlush(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
        outputStream.flush();
    }
}