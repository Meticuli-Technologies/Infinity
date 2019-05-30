package com.meti;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private ObjectInputStream inputStream;

    public ObjectInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    private ObjectOutputStream outputStream;

    public ObjectOutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public Client(int port) throws IOException {
        this(InetAddress.getLocalHost(), port);
    }

    public Client(InetAddress address, int port) throws IOException {
        this(new Socket(address, port));
    }

    public Client(Socket socket) throws IOException {
        this.socket = socket;
    /*
            The OOS must be constructed before the OIS because of the header.
             */
        this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
        this.inputStream = new ObjectInputStream(this.socket.getInputStream());
    }
}