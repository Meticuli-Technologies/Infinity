package com.meti;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient implements Client {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    SocketClient(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    private SocketClient(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    private SocketClient(Socket socket) throws IOException {
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

    void readResponse() throws Throwable {
        processResponse(inputStream.readObject());
    }

    private void processResponse(Object response) throws Throwable {
        if (response instanceof Throwable) {
            throw (Throwable) response;
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

    @Override
    public void write(Serializable serializable) throws IOException {
        outputStream.writeObject(serializable);
    }

    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }
}